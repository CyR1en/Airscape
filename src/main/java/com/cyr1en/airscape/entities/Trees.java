package com.cyr1en.airscape.entities;

import com.cyr1en.airscape.gamestate.PlayingState;
import com.cyr1en.cgdl.BasicGraphics.Draw;
import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;

/**
 * A tree object that's used as an obstacle for the game
 */
public class Trees extends GameObject {

    //color of the leaves
    private Color colorLeaves = new Color(49, 97, 59), colorTrunk = new Color(78, 63, 34);

    //shadow color of the leaves
    private Color colorLeaves2 = new Color(38, 68, 42), colorTrunk2 = new Color(55, 37, 25);

    /**
     * the whole triangle for the leaves
     * /\
     * /__\
     */
    private Polygon leaves;

    /**
     * half triangle for the shadow
     * |\
     * |_\
     */
    private Polygon leavesShadow;

    //height of the tree
    private int height;

    //logic if the player passed the tree
    private boolean passed;

    //tree constructor
    public Trees(int height) {
        this.height = height;
        this.setVector(-3, 0);
        this.setPosition(GamePanel.WIDTH + 100, GamePanel.HEIGHT - height);
        width = 30;
    }

    //returns the current x coordinate of the tree
    public double getX() {
        return x;
    }

    //checks if the player passed the tree
    public boolean isPassed() {
        return passed;
    }

    //player - tree collision detection (not really good...... lol)
    public boolean collided(GameObject o) {
        return ((o.getY() + o.getHeight() <= GamePanel.HEIGHT && o.getY() >= GamePanel.HEIGHT - height) && (o.getX() + o.getWidth() >= x && o.getX() < x + width));
    }

    //updates the tree
    public boolean updateBool() {
        this.lastX = x;
        this.lastY = y;

        x += dx;
        passed = PlayingState.player.getX() > x + 60;

        return x + 150 < 0;
    }

    public void update() {

    }

    //draws the tree
    public void draw(Graphics2D g, float v) {
        //initialize draw class from the CGDL(ethan is amazing)
        int interpolatedX = (int) ((this.x - this.lastX) * v + this.lastX);
        int interpolatedY = (int) ((this.y - this.lastY) * v + this.lastY);
        Draw.init(g);
        width = (int) (height * .09);

        //draw trunk
        Draw.rectangle(interpolatedX, interpolatedY + 50, width, height, colorTrunk);

        //trunk shadow
        Draw.rectangle(interpolatedX + (width / 2), interpolatedY + 50, width - (width / 2), height, colorTrunk2);


        int[] Xs = new int[]{interpolatedX + width / 2, (interpolatedX + width / 2) - (int) (height * 0.3), (interpolatedX + width / 2) + (int) (height * 0.3)};
        int[] Ys = new int[]{GamePanel.HEIGHT - height, GamePanel.HEIGHT - (int) (height * 0.2), GamePanel.HEIGHT - (int) (height * 0.2)};
        leaves = new Polygon(Xs, Ys, 3);


        int[] Xs1 = new int[]{interpolatedX + width / 2, (interpolatedX + width / 2), (interpolatedX + width / 2) + (int) (height * 0.3)};
        int[] Ys1 = new int[]{GamePanel.HEIGHT - height, GamePanel.HEIGHT - (int) (height * 0.2), GamePanel.HEIGHT - (int) (height * 0.2)};
        leavesShadow = new Polygon(Xs1, Ys1, 3);

        //draw leaves
        g.setColor(colorLeaves);
        g.fillPolygon(leaves);

        //draw leaves' shadow
        g.setColor(colorLeaves2);
        g.fillPolygon(leavesShadow);
    }
}
