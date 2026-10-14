package com.amhfilho.games;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld {
	private final List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
	private volatile int width;
	private volatile int height;
	private final Input input;

	public GameWorld(Input input) {
		this.input = input;
	}

	public Input getInput() {
		return input;
	}

	public void addGameObject(GameObject obj) {
		gameObjects.add(obj);
	}

	public void removeGameObject(GameObject obj) {
		gameObjects.remove(obj);
	}

	public List<GameObject> getGameObjects() {
		return Collections.unmodifiableList(gameObjects);
	}

	public void setBounds(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void update(float deltaTime) {
		for (GameObject obj : gameObjects) {
			obj.update(deltaTime, this);
		}
	}

	public void render(Graphics2D g) {
		for (GameObject obj : gameObjects) {
			obj.render(g);
		}
	}
}
