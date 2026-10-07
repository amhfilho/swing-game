package com.amhfilho.games;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private final GamePanel gamePanel;

	public GameFrame(String title, GamePanel gamePanel) {
		super(title);
		this.gamePanel = gamePanel;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null); // Center the window

		add(gamePanel);
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}
}
