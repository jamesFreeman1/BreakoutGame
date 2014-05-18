/**
 * Class for the balls in the Breakout game.
 * 
 * @author James Freeman (1311990)
 * @version April 2014
 */
package gameModel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {

    public int x;
    public int y;
    public final int radius;
    public final Color color;
    public double dirX;
    public double dirY;
    public int speed;

    /**
     * Construct a ball with the given position, size and color.
     *
     * @param x the x coordinates of the ball
     * @param y the y coordinates of the ball
     * @param radius the size of the ball
     * @param color the color of the ball
     */
    public Ball(int x, int y, int radius, Color color) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        Random ran = new Random();
        double num = ran.nextDouble();
        double abs = Math.sqrt(Math.pow(num, 2) + Math.pow(1.0 - num, 2));
        dirX = num / abs;
        dirY = (1.0 - num) / abs;
        this.color = color;
        this.speed = 15;

    }

    /**
     * Draws a circle at (x,y) coordinates with the radius.
     *
     * @param g the graphic being drawn.
     */
    public void paintThis(Graphics g) {
        Color col = Color.BLUE;
        g.setColor(this.color);
        g.fillOval(x, y, (2 * radius), (2 * radius));
        g.setColor(this.color);

    }

    /**
     * Returns the square area the ball occupies at coordinates (x,y).
     *
     * @return the square area the ball occupies at coordinates (x,y).
     */
    public Rectangle getBounds() {
        Rectangle ball = new Rectangle(x, y, (radius * 2), (radius * 2));
        return ball;

    }

    /**
     * Moves the ball to its new (x,y) coordinates based on the dirX and dirY
     * (gradient) it is traveling.
     */
    public void move() {

        this.x = (int) (this.x - (dirX * speed));
        this.y = (int) (this.y - (dirY * speed));

    }

    /**
     * Changes the dirX and dirY values when a the ball hits a surface. If
     * vertical is "true" the value of dirX is changed (If dirX is positive it
     * is changed to negative, and negative if its positive). If horizontal is
     * "true" the value of dirX is changed (If dirX is positive it is changed to
     * negative, and negative if its positive)
     *
     * @param vertical represents if the ball hitting a vertical surface
     * @param horizontal represents if the ball hitting a vertical surface
     */
    public void reflect(boolean vertical, boolean horizontal) {
        dirX = vertical ? dirX : (dirX * -1);
        dirY = horizontal ? dirY : (dirY * -1);

    }
}
