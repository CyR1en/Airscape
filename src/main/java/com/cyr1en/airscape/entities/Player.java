package com.cyr1en.airscape.entities;


import com.cyr1en.cgdl.BasicGraphics.Draw;
import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Entity.Particle;
import com.cyr1en.cgdl.Handlers.ParticleFactory;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.cgdl.util.ImageUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * The player class
 */
public class Player extends GameObject {

    // constant for Y vector (going down)
    private static double GRAVITY = 1.3;

    //variable for all particles that the player generate
    private final ArrayList<Particle> particles;

    //player logic
    private boolean up;
    private boolean crashed;
    private boolean outOfBounds;
    private boolean initial;

    //player image
    private BufferedImage image;

    //player score
    private int score;

    private int c;

    //player constructor
    public Player() {
        this.setVector(0, 0); // initial vector of the player
        width = 80; // initial width of the player
        height = 30; // initial height of the player
        x = 200; // initial X coordinate of the player
        y = GamePanel.HEIGHT / 2 - width; // initial Y coordinate of the player
        image = ImageUtil.loadBufferedImage("/plane.png"); // initialize the image for the player sprite
        score = 0; // initial player score
        crashed = false; // initial crash logic
        particles = new ArrayList<>(); // initialized the Array List of particles
        initial = true; // initial Player state
        ParticleFactory.init(particles); //initialize particle factory
    }

    // update the "up" logic to become the parameter
    public void setUp(boolean b) {
        up = b;
    }

    // sets if it's the player state is initial, or first spawn
    public void setInitial(boolean b) {
        initial = b;
    }

    // increment score
    public void addScore() {
        score++;
    }

    // get the score
    public int getScore() {
        return score;
    }

    // edit the value of logic "crashed"
    public void Crashed(boolean b) {
        crashed = b;
    }

    // return the crashed logic
    public boolean isCrashed() {
        return crashed;
    }

    // return if the player is out of the screen
    public boolean isOutOfBounds() {
        return outOfBounds;
    }

    // updates of the player
    public void update() {
        this.lastX = this.x;
        this.lastY = this.y;
        if (up) {
            dy += -GRAVITY; // incremental Y vector
            if (dy < -20) // minimum of the y coordinate
                dy = -20;
        } else if (!initial) {
            dy += GRAVITY; // incremental y vector
            if (dy > 25) // the maximum player y coordinate
                dy = 25;
        }

        //update y coordinate with the current vector
        y += dy;

        //if player hit the ground -> you crashed
        if (y + height > GamePanel.HEIGHT) {
            crashed = true;
            y = GamePanel.HEIGHT - height;
        }

        //if player gets out of the screen -> you die
        if (y + (height + 15) < 0) {
            outOfBounds = true;
            y = -(height + 15);
        }

        //only draw particle effects of the player when the player is not
        //ont the initial state
        if(!initial) {
            ParticleFactory.createSmoke(x + 10, y + 15, Color.WHITE);
            particles.forEach(Particle::update);
        }
    }

    //draw the Player and all the particle effects
    public void draw(Graphics2D g, float v) {
        for (Particle p : particles)
            p.draw(g, v);
        int interpolatedX = (int)((this.x - this.lastX) * v + this.lastX);
        int interpolatedY = (int)((this.y - this.lastY) * v + this.lastY);
        g.drawImage(image, interpolatedX, interpolatedY - 11, null);
    }
}

