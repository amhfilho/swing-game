package com.amhfilho.games;

import java.awt.Graphics2D;

public interface GameObject {
	void update(float deltaTime);

	void render(Graphics2D g);
}
