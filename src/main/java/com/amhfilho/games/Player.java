package com.amhfilho.games;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player implements GameObject {
	private float x;
	private float y;
	private final int width;
	private final int height;
	private final float speed;

	public Player(float x, float y, int width, int height, float speed) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
	}

	@Override
	public void update(float deltaTime, GameWorld world) {
		Input input = world.getInput();

		if (input.isMouseClicked(MouseEvent.BUTTON1)) {
			x = input.getMouseX() - width / 2f;
			y = input.getMouseY() - height / 2f;
		}

		float dx = 0;
		float dy = 0;
		if (input.isKeyDown(KeyEvent.VK_LEFT) || input.isKeyDown(KeyEvent.VK_A)) dx -= 1;
		if (input.isKeyDown(KeyEvent.VK_RIGHT) || input.isKeyDown(KeyEvent.VK_D)) dx += 1;
		if (input.isKeyDown(KeyEvent.VK_UP) || input.isKeyDown(KeyEvent.VK_W)) dy -= 1;
		if (input.isKeyDown(KeyEvent.VK_DOWN) || input.isKeyDown(KeyEvent.VK_S)) dy += 1;

		x += dx * speed * deltaTime;
		y += dy * speed * deltaTime;

		x = Math.max(0, Math.min(x, world.getWidth() - width));
		y = Math.max(0, Math.min(y, world.getHeight() - height));
	}

	@Override
	public void render(Graphics2D g) {
		Color previous = g.getColor();
		g.setColor(Color.BLUE);
		g.fillRect((int) x, (int) y, width, height);
		g.setColor(previous);
	}
}
