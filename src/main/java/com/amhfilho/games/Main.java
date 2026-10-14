package com.amhfilho.games;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Input input = new Input();
			GameWorld world = new GameWorld(input);

			GamePanel panel = new GamePanel(world);
			GameFrame frame = new GameFrame("Simple bouncing ball", panel);
			frame.setVisible(true);
			input.attachTo(panel);

			float ballX = panel.getCenterX() - 15;
			float ballY = panel.getCenterY() - 15;
			world.addGameObject(new Ball(ballX, ballY, 30, 30, 100f, 100f));
			world.addGameObject(new Player(20, 20, 40, 40, 250f));

			new Game(frame, world, 60).start();
		});
	}
}
