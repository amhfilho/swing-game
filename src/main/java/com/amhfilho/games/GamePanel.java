package com.amhfilho.games;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel {
	private final GameWorld world;

	public GamePanel(GameWorld world) {
		this.world = world;

		// The panel's real size is only known after the window manager reports the
		// frame decorations (asynchronously on X11), so keep the world bounds in sync.
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				world.setBounds(getWidth(), getHeight());
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		world.render((Graphics2D) g);

		// Linux/X11 buffers drawing commands instead of flushing immediately,
		// which causes visible stutter independent of frame timing. Force the flush.
		Toolkit.getDefaultToolkit().sync();
	}

	public float getCenterX() {
		return (float) this.getWidth() / 2;
	}

	public float getCenterY() {
		return (float) this.getHeight() / 2;
	}
}
