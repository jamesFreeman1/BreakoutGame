/**
 * Class for the paddle in the Breakout game.
 *
 * @author James Freeman (1311990)
 * @version April 2014
 */
package gameModel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle {

    public static final int PADDLE_SIZE = 100;
    public static final int PADDLE_THICKNESS = 20;
    public int centreX;
    public int centreY;

    /**
     * Construct a paddle with the given position.
     *
     * @param centreX the x coordinates of the center of the paddle
     * @param centreY the y coordinates of the center of the paddle
     */
    public Paddle(int centreX, int centreY) {
        this.centreX = centreX;
        this.centreY = centreY;

    }

    /**
     * Draws a rectangle at (centreX, centreY) coordinates with the width and
     * hight of paddle.
     *
     * @param g the graphic being drawn.
     */
    public void paintThis(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((centreX - (PADDLE_SIZE / 2)), (centreY - (PADDLE_THICKNESS / 2)), PADDLE_SIZE, PADDLE_THICKNESS);
    }

    /**
     * Returns the area the paddle occupies at coordinates (centreX, centreY).
     *
     * @return the area the paddle occupies at coordinates (centreX, centreY).
     */
    public Rectangle getBounds() {
        Rectangle pad = new Rectangle((centreX - (PADDLE_SIZE / 2)), (centreY - (PADDLE_THICKNESS / 2)), PADDLE_SIZE, PADDLE_THICKNESS);
        return pad;
    }

}
