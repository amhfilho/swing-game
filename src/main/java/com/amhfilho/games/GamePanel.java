package com.amhfilho.games;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

public class GamePanel extends JPanel {
	private final GameWorld world;

	public GamePanel(GameWorld world) {
		this.world = world;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		world.render((Graphics2D) g);

		// Linux/X11 buffers drawing commands instead of flushing immediately,
		// which causes visible stutter independent of frame timing. Force the flush.
		Toolkit.getDefaultToolkit().sync();
	}
}
