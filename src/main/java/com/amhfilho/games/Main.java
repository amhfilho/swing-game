package com.amhfilho.games;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GameWorld world = new GameWorld();
			world.addGameObject(new Box(0, 100, 50, 50, 100f));

			GamePanel panel = new GamePanel(world);
			GameFrame frame = new GameFrame("Simple ball bouncing", panel);
			frame.setVisible(true);
			world.setBounds(panel.getWidth(), panel.getHeight());

			new Game(frame, world, 60).start();
		});
	}
}
