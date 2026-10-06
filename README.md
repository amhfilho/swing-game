# swing-game

A minimal Java Swing game loop skeleton, built around a small set of classes in
`com.amhfilho.games`.

## Design overview

The package is split into three concerns: **running the loop**, **drawing on
screen**, and **what's in the game**.

```
Main
 ├── builds a GameWorld and populates it with GameObjects (e.g. Box)
 ├── builds a GamePanel(world) and wraps it in a GameFrame
 └── starts a Game(frame, world, fps) on its own thread
            │
            ▼
      Game (game loop thread)
        update(deltaTime) ──► GameWorld ──► GameObject.update(deltaTime)
        repaint()          ──► Swing EDT ──► GamePanel.paintComponent()
                                               └─► GameWorld.render(g) ──► GameObject.render(g)
```

### `GameObject`

The contract every entity in the game implements:

- `update(float deltaTime)` — advance the entity's state by the elapsed time.
- `render(Graphics2D g)` — draw the entity's current state.

Keeping this as a two-method interface means `GameWorld` and `Game` never need
to know about concrete entity types — adding a new kind of object (enemy,
projectile, etc.) only means implementing `GameObject`.

### `Box`

The only current `GameObject` implementation: a rectangle that moves
horizontally at a constant `speedX`. It's a reference example of the
interface — `x`/`y` are mutable state, `width`/`height`/`speedX` are fixed at
construction.

### `GameWorld`

Owns the collection of active `GameObject`s and fans `update`/`render` calls
out to all of them. It uses a `CopyOnWriteArrayList` because the collection is
read from one thread and written from another (see threading below) —
objects can be added/removed while a frame is mid-update or mid-render
without throwing `ConcurrentModificationException`.

### `Game`

The simulation clock. `Game` implements `Runnable` and runs on its own daemon
thread, started by `start()`. Each loop iteration:

1. Computes `deltaTime` (seconds since the previous frame) from
   `System.nanoTime()`.
2. Calls `world.update(deltaTime)` — a **fixed/variable timestep** update, so
   object movement speed is frame-rate independent.
3. Calls `frame.getGamePanel().repaint()` to schedule a redraw.
4. Sleeps for whatever's left of the target frame budget (`1s / fps`) to cap
   the loop rate.

Note that `update` happens on the game thread, while the actual painting
(`paintComponent`) happens later on Swing's Event Dispatch Thread once
`repaint()` is processed — `GameWorld`'s `CopyOnWriteArrayList` exists
precisely to make that cross-thread access safe.

### `GamePanel` / `GameFrame`

Thin Swing glue: `GamePanel` is the `JPanel` whose `paintComponent` delegates
straight to `world.render(g)`, then forces a `Toolkit.sync()` flush (needed on
Linux/X11, where drawing commands are otherwise buffered and cause visible
stutter). `GameFrame` is the `JFrame` that hosts the panel and sets up the
window (size, close behavior, centering).

### `Main`

Wires everything together on the Swing EDT (`SwingUtilities.invokeLater`):
build a `GameWorld`, seed it with `GameObject`s, wrap it in a
`GamePanel`/`GameFrame`, show the window, then start the `Game` loop.

## Adding a new game object

Implement `GameObject`, give it whatever state it needs, and add an instance
to the `GameWorld` (currently done in `Main`). No other class needs to
change.
