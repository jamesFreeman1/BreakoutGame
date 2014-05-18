/**
 * Class for the bricks in the Breakout game.
 *
 * @author James Freeman (1311990)
 * @version April 2014
 */
package gameModel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Brick {

    public final int topLeftX, topLeftY, width, height;
    public Color color;

    /**
     * Construct a brick with the given position, size and color.
     *
     * @param topLeftX the x coordinates of the top left corner of the brick
     * @param topLeftY the y coordinates of the top left corner of the brick
     * @param width the width of the brick
     * @param height the height of the brick
     * @param color of the brick
     */
    public Brick(int topLeftX, int topLeftY, int width, int height, Color color) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * Draws a rectangle at (topLeftX,topLeftY) coordinates with the width and
     * hight of brick.
     *
     * @param g the graphic being drawn.
     */
    public void paintThis(Graphics g) {
        g.setColor(color);
        g.fillRect(topLeftX, topLeftY, width, height);
    }

    /**
     * Returns the area the brick occupies at coordinates (topLeftX,topLeftY).
     *
     * @return the area the brick occupies at coordinates (topLeftX,topLeftY).
     */
    public Rectangle getBounds() {
        Rectangle bri = new Rectangle(topLeftX, topLeftY, width, height);
        return bri;
    }

}
