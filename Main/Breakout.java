/**
 * Main class for Breakout game.
 *
 * ABOUT GAME:
 *
 * Working runnable breakout game.
 *
 * Game contains 8 rows of 16 bricks (similar to game pictured in assignment).
 *
 * Game starts with one red ball on the one red paddle.
 *
 * Paddle is moved from left and right using the left and right arrow keys.
 *
 * A new game begins when the spacebar is pressed, this releases the ball.
 *
 * The ball rebounds off the paddle, game walls and bricks.
 *
 * When a ball hits a brick that brick is removed from the game.
 *
 * When there are no more balls in play then a "You Lose!" message is displayed.
 *
 * When all bricks are removed from the game a "You Win!" message is displayed.
 *
 * Extension 1) The paddle is split into 5 segments. When the ball hits the
 * paddle it will have a specific direction depending on the paddle segment it
 * hits. Each segment on the paddle is roughly 20 pixels wide. (Working from the
 * left hand side of the paddle) Far left paddle segment results in the ball
 * traveling at a low gradient left, next segment the ball will travel at a high
 * gradient left, the next segment (the center) the ball will travel straight
 * up, the next segment the ball will travel at a high gradient right and the
 * far right segment results in the ball traveling at a low gradient right.
 *
 * Extension 2) There is a special brick colored PINK. When the ball hits this
 * brick and removes it, two additional balls are added to the game.
 *
 * @author James Freeman (1311990)
 * @version April 2014
 */
package Main;

import gameModel.Ball;
import gameModel.Brick;
import gameModel.Paddle;
import static gameModel.Paddle.PADDLE_SIZE;
import gameModel.PaintingPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Breakout extends TimerTask {

    public Vector<Brick> bricks;
    public Vector<Ball> balls;
    public Ball ball;
    public Paddle paddle;
    public PaintingPanel paintingPanel;
    public boolean gameRunning;
    public boolean SetUp;

    /**
     * Constructs the breakout game.
     *
     */
    public Breakout() {
        Vector<Object> contents = new Vector<Object>();
        SetUp(contents);

        paintingPanel = new PaintingPanel(contents);
        paintingPanel.setOpaque(false);
        paintingPanel.setBackground(Color.BLACK);
        paintingPanel.setPreferredSize(new Dimension(800, 600));
        paintingPanel.isLoser = false;
        paintingPanel.isWinner = false;

        showGui(paintingPanel, this);
        paintingPanel.repaint();

    }

    /**
     * Constructs the elements that will be painted on the panel ( The bricks,
     * ball and paddle)
     *
     * @param Vector<Object> the vector that will contain items to be created
     * painted and painted onto the panel.
     */
    private void SetUp(Vector<Object> contents) {
        this.bricks = new Vector<Brick>();

        this.paddle = new Paddle(400, 560);
        contents.add(paddle);

        /**
         * The brick number that will be a different color and produce 2
         * additional balls when the ball collides with it.
         */
        int ballBrick = 97;

        /**
         * Creates 8 rows and 13 columns of bricks. Bricks are given a color
         * depending on what row they are in. When each brick is added they
         * added to both the bricks and contents vectors.
         *
         */
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 13; col++) {
                Color color = Color.BLUE;
                if (row > 0 && row <= 4) {
                    color = Color.ORANGE;
                } else if (row > 4) {
                    color = Color.GREEN;
                }
                Brick brick = new Brick(col * 60 + 15, row * 30 + 15, 55, 22, color);
                bricks.add(brick);

                /**
                 * When brick that will spawn the additional balls is created it
                 * is assigned a pink color.
                 */
                if (bricks.size() == ballBrick) {
                    brick.color = Color.PINK;
                }
                contents.add(brick);
            }
        }

        /**
         * A ball vector is initiated to contain all balls. The starting ball is
         * created and added to the contents vector.
         */
        balls = new Vector<Ball>();
        ball = new Ball(paddle.centreX, paddle.centreY - 20, 5, Color.RED);
        balls.add(ball);
        contents.add(ball);
        SetUp = true;

    }

    /**
     * Constructs the elements that will be painted on the panel ( The bricks,
     * ball and paddle)
     *
     * @param panel the Jpanel that will be added to the JFrame.
     * @param breakout the Breakout class that contains the KeyboardHandler.
     * Required in order to call add a KeyListner from a static position.
     */
    public static void showGui(JPanel panel, Breakout breakout) {

        JFrame frame = new JFrame("Breakout");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.addKeyListener(breakout.new KeyboardHandler());
        frame.pack();
        frame.setVisible(true);
        frame.repaint();

    }

    /**
     * Constructs a private internal class for the KeyboardHandler.
     */
    private class KeyboardHandler extends KeyAdapter {

        /**
         * Overrides the KeyboardHandler keyPressed method so that the paddle is
         * moved left and right when the left and right keys are pressed
         * accordingly. Additionally the breakout game starts when the spacebar
         * is pressed.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    paddleMoveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    paddleMoveRight();
                    break;
                case KeyEvent.VK_SPACE:
                    beginGame();
                    break;
            }

        }
    }

    /**
     * Moves the paddle left by 10 pixels (decreases the centreX coordinates by
     * 10)
     */
    public void paddleMoveLeft() {
        if (this.gameRunning) {
            if (this.paddle.centreX > 50) {
                this.paddle.centreX -= 10;
            }
            paintingPanel.repaint();
        }
    }

    /**
     * Moves the paddle right by 10 pixels (increases the centreX coordinates by
     * 10)
     */
    public void paddleMoveRight() {
        if (this.gameRunning) {
            if (this.paddle.centreX + 60 < 800) {
                this.paddle.centreX += 10;
            }
            paintingPanel.repaint();
        }
    }

    /**
     * Changes the direction of a ball based on where and what surface it
     * collides with.
     */
    public void handleCollisions() {
//No more bricks, the win message is displayed.
        if (bricks.size() == 0) {
            stopGame();
            paintingPanel.isWinner = true;
        }

        boolean theBrickContainingBalls = false;
        int removeBallIndex = 1;
        for (Ball ball : balls) {
            if (ball.y > 600) {
                removeBallIndex = balls.indexOf(ball);
            }
            //Ball collides with a wall 
            if (ball.x < 0 || ball.x > 800 - ball.radius * 2) {
                ball.reflect(false, true);
                System.out.println("reflected of wall:");
            }
            //Ball collides with the roof of the game
            if (ball.y < 0) {
                ball.reflect(true, false);
                System.out.println("reflected of roof:");
            }
            //Ball collides with the paddle
            if (this.paddle.getBounds().intersects(ball.getBounds())) {
                /**
                 * Ball collides with the center of the paddle. The balls dirX
                 * is then set to 0 (ball goes straight up)
                 */
                if ((paddle.centreX - 10) <= (ball.x + ball.radius) && (ball.x + ball.radius) <= (paddle.centreX + 10)) {
                    ball.dirX = 0;
                }
                /**
                 * Ball collides with the second furtherest right segment of the
                 * paddle The balls dirX is then set to -0.5 (ball goes right at
                 * a high gradient.
                 */
                if ((this.paddle.centreX + 11) <= (ball.x + ball.radius) && (ball.x + ball.radius) <= (paddle.centreX + 30)) {
                    ball.dirX = -0.5;
                }
                /**
                 * Ball collides with the far right segment of the paddle. The
                 * balls dirX is then set to -0.8 (ball goes right at a low
                 * gradient.
                 */
                if ((this.paddle.centreX + 31) <= (ball.x + ball.radius)) {
                    ball.dirX = -0.8;
                }
                /**
                 * Ball collides with the second furtherest left segment of the
                 * paddle The balls dirX is then set to 0.5 (ball goes left at a
                 * high gradient.
                 */
                if ((this.paddle.centreX - 30) <= (ball.x + ball.radius) && (ball.x + ball.radius) <= (paddle.centreX - 11)) {
                    ball.dirX = 0.5;
                }
                /**
                 * Ball collides with the far left segment of the paddle. The
                 * balls dirX is then set to 0.8 (ball goes left at a low
                 * gradient.
                 */
                if ((this.paddle.centreX - 31) >= (ball.x + ball.radius)) {
                    ball.dirX = 0.8;
                }

                ball.reflect(true, false);
                System.out.println("reflected of paddle: ");
            }

//Ball collides with a brick
            for (Brick brick : bricks) {
                if (ball.getBounds().intersects(brick.getBounds())) {
                    System.out.println("reflected of Brick");
                    bricks.remove(brick);
                    paintingPanel.contents.remove(brick);
//Ball collides with the vertcal side of a brick
                    if (this.ball.x < brick.topLeftX || this.ball.x > -brick.topLeftX) {
                        ball.reflect(false, true);
                        System.out.println("reflected off side of brick");
                    }
//Ball collides with the horizontal side of a brick
                    ball.reflect(true, false);
                    System.out.println("reflected off top/bottom brick");

                    if (brick.color == Color.PINK) {
                        theBrickContainingBalls = true;
                    }
                    break;
                }
            }
        }
        if (removeBallIndex != 1) {
            balls.remove(removeBallIndex);
        }
//No more balls in the balls vector, the lose message is shown.
        if (balls.size() == 0) {
            stopGame();
            paintingPanel.isLoser = true;
        }
        /**
         * Ball collides with the special "PINK" brick - 2 additional bricks are
         * created and added to the balls vector
         */
        if (theBrickContainingBalls) {
            Ball ball1 = new Ball(paddle.centreX, paddle.centreY - 20, 5, Color.RED);
            balls.add(ball1);
            paintingPanel.contents.add(ball1);
            Ball ball2 = new Ball(paddle.centreX, paddle.centreY - 20, 5, Color.RED);
            balls.add(ball2);
            paintingPanel.contents.add(ball2);
        }
    }

    /**
     * Stops the game (sets gameRunning and SetUp to false).
     */
    public void stopGame() {
        gameRunning = false;
        SetUp = false;
    }

    /**
     * Checks if the game is running or not set- up. Creates the contents Vector
     * that paintingPanel will accept to paint create all objects within the
     * game.
     */
    public void beginGame() {
        if (!SetUp || gameRunning) {
            Vector<Object> contents = new Vector<Object>();
            SetUp(contents);
            paintingPanel.contents = contents;
            paintingPanel.isLoser = false;
            paintingPanel.isWinner = false;
        }
        gameRunning = true;
    }

    /**
     * The timer is initiated. The timer will action the breakout run method
     * every 40 milli seconds.
     */
    public static void main(String[] args) {

        Timer t = new Timer();
        t.schedule(new Breakout(), 0, 40);
    }

    /**
     * The game is checked if it is running. If running then the balls in balls
     * vector will function and move accordingly. Will check if any balls
     * collide with any objects, if so they will re-act accordingly. The panel
     * is repainted with all its contents.
     */
    @Override
    public void run() {
        if (gameRunning) {
            for (Ball ball : balls) {
                ball.move();
            }
            handleCollisions();
            paintingPanel.repaint();
        }
    }

}
