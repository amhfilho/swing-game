package org.example;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private float positionX = 0.0f;
	private final float speedX = 100.0f; // Move 100 pixels per second

	public void update(float deltaTime) {
		positionX += speedX * deltaTime;
		repaint(); // Request a repaint to visualize the updated position
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		g.fillRect((int) positionX, 100, 50, 50); // Draw a rectangle at the current position
	}
}
