package com.amhfilho.games;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Ball implements GameObject {
    private float x;
    private float y;
    private final int width;
    private final int height;
    private float speedX;
    private float speedY;

    public Ball(float x, float y, int width, int height, float speedX, float speedY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    @Override
    public void update(float deltaTime, GameWorld world) {
        x+= speedX * deltaTime;
        y-= speedY * deltaTime;

        if (y < 0) {
            y = 0;
            speedY = -speedY;
        }
        else if (x + width > world.getWidth()) {
            x = world.getWidth() - width;
            speedX = -speedX;
        }
        else if (y + height > world.getHeight()) {
            y = world.getHeight() - height;
            speedY = -speedY;
        }
        else if (x < 0) {
            x = 0;
            speedX = -speedX;
        }
    }

    @Override
    public void render(Graphics2D g) {
        Shape circle = new Ellipse2D.Double(x, y, width, height);
        g.draw(circle);
        g.fill(circle);
    }
}
