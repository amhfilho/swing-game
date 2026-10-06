package com.amhfilho.games;

import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld {
	private final List<GameObject> gameObjects = new CopyOnWriteArrayList<>();

	public void addGameObject(GameObject obj) {
		gameObjects.add(obj);
	}

	public void removeGameObject(GameObject obj) {
		gameObjects.remove(obj);
	}

	public void update(float deltaTime) {
		for (GameObject obj : gameObjects) {
			obj.update(deltaTime);
		}
	}

	public void render(Graphics2D g) {
		for (GameObject obj : gameObjects) {
			obj.render(g);
		}
	}
}
