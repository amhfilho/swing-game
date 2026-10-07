package com.amhfilho.games;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GameWorld world = new GameWorld();

			GamePanel panel = new GamePanel(world);
			GameFrame frame = new GameFrame("Simple bouncing ball", panel);
			frame.setVisible(true);
			world.setBounds(panel.getWidth(), panel.getHeight());

			float ballX = panel.getCenterX() - 15;
			float ballY = panel.getCenterY() - 15;
			world.addGameObject(new Ball(ballX, ballY, 30, 30, 100f, 100f));

			new Game(frame, world, 60).start();
		});
	}
}
