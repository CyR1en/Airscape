package com.cyr1en.airscape.entities;

import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.cgdl.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * plane obstacles that spawns with the trees
 */
public class Planes extends GameObject {

    // Obstacle plan sprite
    private BufferedImage image;

    /**
     * Plane constructor
     * @param ySpawn the spawn Y coordinate of the plane
     */
    public Planes(int ySpawn) {
        width = 80; // width of the plane
        height = 30; // height of the plane
        this.setVector(-5, 0); // vector of the plane
        this.setPosition(GamePanel.WIDTH + 100, ySpawn); // initial position of the plane
        image = ImageUtil.loadBufferedImage("/EnemyPlane.png"); // initialize the image sprite
    }

    //update the plane. The plane moves to the left
    public boolean updateBool() {
        this.lastX = this.x;
        this.lastY = this.y;
        x += dx;
        return x + width < 0;
    }


    // if this plane collides with the object in the parameter
    public boolean collided(GameObject object) {
        return this.intersects(object);
    }

    @Override
    public void update() {
    }

    //draw the plane
    public void draw(Graphics2D g, float v) {
        int interpolatedX = (int)((this.x - this.lastX) * v + this.lastX);
        int interpolatedY = (int)((this.y - this.lastY) * v + this.lastY);
        g.drawImage(image, interpolatedX, interpolatedY - 11, null);
    }
}
