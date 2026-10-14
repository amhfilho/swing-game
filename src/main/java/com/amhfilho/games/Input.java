package com.amhfilho.games;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Collects keyboard and mouse state from Swing and exposes it to game objects
 * through a polling API.
 * <p>
 * Swing listeners run on the EDT and only record state into thread-safe sets.
 * The game loop calls {@link #poll()} once per frame, which snapshots that
 * state so every object sees the same input for the whole frame.
 */
public class Input {
	// Written on the EDT
	private final Set<Integer> keysDown = ConcurrentHashMap.newKeySet();
	private final Set<Integer> keysPressedSincePoll = ConcurrentHashMap.newKeySet();
	private final Set<Integer> buttonsDown = ConcurrentHashMap.newKeySet();
	private final Set<Integer> buttonsPressedSincePoll = ConcurrentHashMap.newKeySet();
	private volatile int mouseX;
	private volatile int mouseY;

	// Per-frame snapshot, read on the game-loop thread
	private final Set<Integer> frameKeysDown = new HashSet<>();
	private final Set<Integer> frameKeysPressed = new HashSet<>();
	private final Set<Integer> frameButtonsDown = new HashSet<>();
	private final Set<Integer> frameButtonsPressed = new HashSet<>();
	private int frameMouseX;
	private int frameMouseY;

	public void attachTo(Component component) {
		component.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// add() returns false on OS key repeat, so "pressed" fires once per press
				if (keysDown.add(e.getKeyCode())) {
					keysPressedSincePoll.add(e.getKeyCode());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keysDown.remove(e.getKeyCode());
			}
		});

		MouseAdapter mouse = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				updatePosition(e);
				buttonsDown.add(e.getButton());
				buttonsPressedSincePoll.add(e.getButton());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				updatePosition(e);
				buttonsDown.remove(e.getButton());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				updatePosition(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				updatePosition(e);
			}
		};
		component.addMouseListener(mouse);
		component.addMouseMotionListener(mouse);

		// Without this, keys held during alt-tab would stay "down" forever
		component.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				keysDown.clear();
				buttonsDown.clear();
			}
		});

		component.setFocusable(true);
		component.requestFocusInWindow();
	}

	/** Snapshots the current input state. Called once per frame by the game loop. */
	public void poll() {
		snapshot(keysDown, keysPressedSincePoll, frameKeysDown, frameKeysPressed);
		snapshot(buttonsDown, buttonsPressedSincePoll, frameButtonsDown, frameButtonsPressed);
		frameMouseX = mouseX;
		frameMouseY = mouseY;
	}

	/** True while the key is held down. */
	public boolean isKeyDown(int keyCode) {
		return frameKeysDown.contains(keyCode);
	}

	/** True only on the frame the key was first pressed. */
	public boolean isKeyPressed(int keyCode) {
		return frameKeysPressed.contains(keyCode);
	}

	/** True while the mouse button (e.g. {@link MouseEvent#BUTTON1}) is held down. */
	public boolean isMouseDown(int button) {
		return frameButtonsDown.contains(button);
	}

	/** True only on the frame the mouse button was first pressed. */
	public boolean isMouseClicked(int button) {
		return frameButtonsPressed.contains(button);
	}

	public int getMouseX() {
		return frameMouseX;
	}

	public int getMouseY() {
		return frameMouseY;
	}

	private void updatePosition(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	private static void snapshot(Set<Integer> down, Set<Integer> pressedSincePoll,
			Set<Integer> frameDown, Set<Integer> framePressed) {
		framePressed.clear();
		for (Integer code : pressedSincePoll) {
			pressedSincePoll.remove(code);
			framePressed.add(code);
		}
		frameDown.clear();
		frameDown.addAll(down);
		// A tap shorter than one frame still counts as down for that frame
		frameDown.addAll(framePressed);
	}
}
