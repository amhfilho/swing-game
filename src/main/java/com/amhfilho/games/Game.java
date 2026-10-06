package com.amhfilho.games;

public class Game implements Runnable {
	private final GameFrame frame;
	private final GameWorld world;
	private final int fps;
	private volatile boolean running = false;

	public Game(GameFrame frame, GameWorld world, int fps) {
		this.frame = frame;
		this.world = world;
		this.fps = fps;
	}

	public void start() {
		running = true;
		Thread gameThread = new Thread(this, "game-loop");
		gameThread.setDaemon(true);
		gameThread.start();
	}

	public void stop() {
		running = false;
	}

	@Override
	public void run() {
		long frameTimeNanos = 1_000_000_000L / fps;
		long lastTime = System.nanoTime();

		while (running) {
			long frameStart = System.nanoTime();

			float deltaTime = (frameStart - lastTime) / 1_000_000_000.0f;
			lastTime = frameStart;

			world.update(deltaTime);
			frame.getGamePanel().repaint();

			long elapsed = System.nanoTime() - frameStart;
			long sleepNanos = frameTimeNanos - elapsed;
			if (sleepNanos > 0) {
				try {
					Thread.sleep(sleepNanos / 1_000_000, (int) (sleepNanos % 1_000_000));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					running = false;
				}
			}
		}
	}
}
