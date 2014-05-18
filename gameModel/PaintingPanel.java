/**
 * Class for the panel that has all game components painted to, in the Breakout
 * game.
 *
 * @author James Freeman (1311990)
 * @version April 2014
 */
package gameModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JPanel;

public class PaintingPanel extends JPanel {

    public Vector<Object> contents;

    public boolean isLoser;
    public boolean isWinner;

    /**
     * Constructs panel that accepts a Vector containing objects.
     *
     * @param contents the vector that will contain items to be painted onto
     * panel.
     */
    public PaintingPanel(Vector<Object> contents) {
        super(true);
        this.contents = contents;

    }

    /**
     * Paints objects within the contents vector and paints a win or lose line
     * of text based on whether the player wins of looses the game.
     *
     * @param g the graphics being drawn.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        setBackground(Color.BLACK);
        for (Object content : contents) {

            if (content instanceof Ball) {
                Ball ball = (Ball) content;
                ball.paintThis(g);
            } else if (content instanceof Brick) {
                Brick brick = (Brick) content;
                brick.paintThis(g);
            } else if (content instanceof Paddle) {
                Paddle paddle = (Paddle) content;
                paddle.paintThis(g);
            }

//Message to be dispayed if the player loses the game
            if (isLoser) {
                g.setColor(Color.red);
                Font font = new Font("Serif", Font.BOLD, 48);
                g.setFont(font);
                int width = g.getFontMetrics().stringWidth("You Lose!");
                g.drawString("You Lose!", (getWidth() - width) / 2, getHeight() / 2);
            }
//Message to be dispayed if the player wins the game           
            if (isWinner) {
                g.setColor(Color.red);
                Font font = new Font("Serif", Font.BOLD, 48);
                g.setFont(font);
                int width = g.getFontMetrics().stringWidth("You Win!");
                g.drawString("You Win!", (getWidth() - width) / 2, getHeight() / 2);
            }
        }
    }

}
