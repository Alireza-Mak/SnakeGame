import Components.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The SnakeView class represents the graphical user interface for the Snake game.
 * It extends JPanel and handles the rendering of the game board, the snake, the apple,
 * and the game state, including game over and score display.
 *
 * @author Alireza Mak
 * @version 1.0
 * @since 2024-08-06
 */
public class SnakeView extends JPanel {
    // Constants for screen dimensions, unit size, and button properties
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int RESET_BUTTON_WIDTH = 180;
    private static final int RESET_BUTTON_HEIGHT = 45;
    private static final int RESET_BUTTON_BORDER_WIDTH = 2;
    private static final Font SCORE_FONT = new Font("Ink Free", Font.BOLD, 25);
    private static final Font GAME_OVER_FONT = new Font("Ink Free", Font.BOLD, 55);
    private static final Font FINAL_SCORE_FONT = new Font("Ink Free", Font.BOLD, 35);

    private JFrame frame;
    private final Map<String, Integer> screenProperties;
    private int appleX;
    private int appleY;
    private int[] snakeX;
    private int[] snakeY;
    private int score;
    private boolean isRunning;
    private CustomButton resetButton;

    /**
     * Constructor for SnakeView class. Initializes the screen properties,
     * sets up the panel, and initializes the main game frame and reset button.
     */
    public SnakeView() {
        screenProperties = new HashMap<>() {
            {
                put("SCREEN_WIDTH", SCREEN_WIDTH);
                put("SCREEN_HEIGHT", SCREEN_HEIGHT);
                put("UNIT_SIZE", UNIT_SIZE);
            }
        };
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        initializeMainFrame();
        initializeResetButton();
    }

    /**
     * set the X-coordinates of the snake.
     *
     * @param snakeX An array containing the X-coordinates of the snake.
     */
    public void setSnakeX(int[] snakeX) {
        this.snakeX = snakeX;
    }

    /**
     * set the Y-coordinates of the snake.
     *
     * @param snakeY An array containing the Y-coordinates of the snake.
     */
    public void setSnakeY(int[] snakeY) {
        this.snakeY = snakeY;
    }

    /**
     * set the X-coordinate of the apple.
     *
     * @param appleX An int containing the X-coordinate of the apple.
     */
    public void setAppleX(int appleX) {
        this.appleX = appleX;
    }

    /**
     * set the Y-coordinate of the apple.
     *
     * @param appleY An int containing the Y-coordinate of the apple.
     */
    public void setAppleY(int appleY) {
        this.appleY = appleY;
    }

    /**
     * set the Y-coordinate of the apple.
     *
     * @param score A number containing user's score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * set the Y-coordinate of the apple.
     *
     * @param isRunning A boolean to show  whether the game is running.
     */
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
        resetButton.setVisible(!isRunning);
    }

    /**
     * get the Y-coordinate of the apple.
     */
    public CustomButton getResetButton() {
        return resetButton;
    }

    /**
     * get the screen properties like width- height and unit.
     */
    public Map<String, Integer> getScreenProperties() {
        return screenProperties;
    }

    /**
     * Initializes the main game window frame, setting up its properties and making it visible.
     */
    private void initializeMainFrame() {
        frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Displays a confirmation dialog when the user attempts to exit the game.
     * If confirmed, closes the game window and exits the program.
     */
    public void showExitConfirmation() {
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?", "Exit Program Message Box", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            frame.dispose();
            System.exit(0);
        }
    }

    /**
     * Paints the game components on the panel, including the grid, apple, snake, and score.
     * Also handles the game over state display.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        if (isRunning) {
            drawGame(g);
        } else {
            drawGameOver(g);
        }
    }

    /**
     * Draws the grid lines on the game board.
     *
     * @param g The Graphics object used for painting.
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int x = 0; x < SCREEN_WIDTH / UNIT_SIZE; x++) {
            g.drawLine(x * UNIT_SIZE, 0, x * UNIT_SIZE, SCREEN_HEIGHT);
        }
        for (int y = 0; y < SCREEN_HEIGHT / UNIT_SIZE; y++) {
            g.drawLine(0, y * UNIT_SIZE, SCREEN_WIDTH, y * UNIT_SIZE);
        }
    }

    /**
     * Draws the game elements, including the apple, snake, and score.
     *
     * @param g The Graphics object used for painting.
     */
    private void drawGame(Graphics g) {
        drawApple(g);
        drawSnake(g);
        drawScore(g);
    }

    /**
     * Draws the apple on the game board.
     *
     * @param g The Graphics object used for painting.
     */
    private void drawApple(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
    }

    /**
     * Draws the snake on the game board. The head of the snake is red, while the body
     * segments are colored randomly.
     *
     * @param g The Graphics object used for painting.
     */
    private void drawSnake(Graphics g) {
        for (int i = 0; i < snakeX.length; i++) {
            if (i == 0) {
                g.setColor(Color.RED);
                g.fillRoundRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            } else {
                g.setColor(new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
                g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    /**
     * Draws the current score on the game board.
     *
     * @param g The Graphics object used for painting.
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(SCORE_FONT);
        String scoreString = "Score: " + score;
        FontMetrics scoreFontMetrics = g.getFontMetrics(SCORE_FONT);
        g.drawString(scoreString, (SCREEN_WIDTH - scoreFontMetrics.stringWidth(scoreString)) / 2, SCORE_FONT.getSize());
    }

    /**
     * Draws the game over message and final score when the game is over.
     *
     * @param g The Graphics object used for painting.
     */
    private void drawGameOver(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(GAME_OVER_FONT);
        String gameOverString = "Game Over";
        FontMetrics gameOverFontMetrics = g.getFontMetrics(GAME_OVER_FONT);
        g.drawString(gameOverString, (SCREEN_WIDTH - gameOverFontMetrics.stringWidth(gameOverString)) / 2, SCREEN_HEIGHT / 2);

        g.setFont(FINAL_SCORE_FONT);
        String scoreString = "Your final score is " + score;
        FontMetrics scoreFontMetrics = g.getFontMetrics(FINAL_SCORE_FONT);
        g.drawString(scoreString, (SCREEN_WIDTH - scoreFontMetrics.stringWidth(scoreString)) / 2, SCREEN_HEIGHT / 2 + scoreFontMetrics.getHeight());
    }


    /**
     * Initializes the reset button, setting its properties, position, and font.
     * The button is initially hidden and will be made visible when the game is over.
     */
    private void initializeResetButton() {
        resetButton = new CustomButton("Play Again", RESET_BUTTON_BORDER_WIDTH);
        resetButton.setBounds((SCREEN_WIDTH - RESET_BUTTON_WIDTH) / 2, SCREEN_HEIGHT / 2 + RESET_BUTTON_HEIGHT * 2, RESET_BUTTON_WIDTH, RESET_BUTTON_HEIGHT);
        resetButton.setFont(new Font("Ink Free", Font.BOLD, 25));
        resetButton.setVisible(false);
        setLayout(null);
        add(resetButton);
    }

    /**
     * Adds a mouse listener to the reset button for handling click events.
     *
     * @param mouseListener The mouse listener to be added to the reset button.
     */
    public void addResetButtonListener(MouseListener mouseListener) {
        resetButton.addMouseListener(mouseListener);
    }
}
