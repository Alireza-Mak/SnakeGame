import Components.Obstacle;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;


/**
 * The SnakeModel class represents the game's state and logic.
 * It handles the snake's movement, collision detection, and apple generation.
 *
 * @author Alireza Mak
 * @version 1.0
 * @since 2024-08-06
 */
public class SnakeModel {
    private final Random random;
    private final int DEFAULT_DELAY;
    private final int START_LENGTH;
    Map<String, Integer> DIFFICULTIES_TYPES = new HashMap<>() {{
        put("EASY", 1);
        put("MEDIUM", 3);
        put("HARD", 4);
    }};
    private Map<String, Integer> screenProperties;
    private int appleX;
    private int appleY;
    ArrayList<ArrayList<Point>> obstacles= new ArrayList<>();
    private int snakeLength;
    private int[] snakeX;
    private int[] snakeY;
    private char direction;
    private Timer timer;
    private int delay;
    private int score;
    private boolean isRunning;
    private String difficulty;
    private int delayStep;

    /**
     * Constructor to initialize the SnakeModel.
     */
    public SnakeModel() {
        random = new Random();
        this.DEFAULT_DELAY = 100;
        this.START_LENGTH = 4;
        setDifficulty(DIFFICULTIES_TYPES.keySet().iterator().next());
    }

    /**
     * Sets the screen properties from the view.
     *
     * @param screenProperties A map containing screen properties.
     */
    public void setScreenDetails(Map<String, Integer> screenProperties) {
        this.screenProperties = screenProperties;
    }

    /**
     * Sets the direction of the snake's movement.
     *
     * @param direction The direction of the snake ('U', 'D', 'L', 'R').
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /**
     * Gets the current direction of the snake's movement.
     *
     * @return The direction of the snake.
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Gets the current score of the game.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the list of obstacle points.
     *
     * @return a {@link ArrayList} of {@link ArrayList} of {@link Point} objects
     */
    public ArrayList<ArrayList<Point>> getObstacles() {
        return obstacles;
    }

    /**
     * Checks if the game is running.
     *
     * @return True if the game is running, false otherwise.
     */
    public boolean getIsRunning() {
        return isRunning;
    }

    /**
     * Gets the X-coordinates of the snake.
     *
     * @return The X-coordinates array.
     */
    public int[] getSnakeX() {
        return snakeX;
    }

    /**
     * Gets the Y-coordinates of the snake.
     *
     * @return The Y-coordinates array.
     */
    public int[] getSnakeY() {
        return snakeY;
    }

    /**
     * Gets the Y-coordinate of the apple.
     *
     * @return The apple's Y-coordinate.
     */
    public int getAppleY() {
        return appleY;
    }

    /**
     * Gets the X-coordinate of the apple.
     *
     * @return The apple's X-coordinate.
     */
    public int getAppleX() {
        return appleX;
    }


    /**
     * Gets the game's timer.
     *
     * @return The timer.
     */
    public Timer getTimer() {
        return this.timer;
    }

    /**
     * Starts a new game by initializing the snake and setting the game state.
     *
     * @param actionListener The ActionListener for the game timer.
     */
    public void startGame(ActionListener actionListener) {
        score = 0;
        snakeLength = START_LENGTH;
        this.snakeX = new int[snakeLength];
        this.snakeY = new int[snakeLength];
        direction = 'R';
        isRunning = true;
        delay = DEFAULT_DELAY;
        timer = new Timer(delay, actionListener);
        timer.start();
        createApple();
        createObstacles();
    }

    /**
     * Creates a new apple at a random position on the screen.
     */
    private void createApple() {
        do {
            appleX = generateRandomPosition(screenProperties.get("SCREEN_WIDTH"));
            appleY = generateRandomPosition(screenProperties.get("SCREEN_HEIGHT"));
        } while (checkObstacleCollision(appleX, appleY));


    }

    /**
     * Creates a new apple at a random position on the screen.
     */
    private void createObstacles() {
        int width = screenProperties.get("SCREEN_HEIGHT");
        int height = screenProperties.get("SCREEN_HEIGHT");
        int unitSize = screenProperties.get("UNIT_SIZE");
        Obstacle obstacle = new Obstacle(width, height, unitSize);
        obstacles = obstacle.obstaclesGenerator(DIFFICULTIES_TYPES.get(difficulty));
    }

    /**
     * Generates a random position for the apple within the screen limits.
     *
     * @param maxSize The maximum size (width or height) of the screen.
     * @return The random position.
     */
    private int generateRandomPosition(int maxSize) {
        return random.nextInt(maxSize / screenProperties.get("UNIT_SIZE")) * screenProperties.get("UNIT_SIZE");
    }

    /**
     * Moves the snake in the current direction.
     *
     * @param unitSize The size of each unit the snake moves.
     */
    public void moveSnake(int unitSize) {
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        switch (direction) {
            case 'R':
                snakeX[0] += unitSize;
                break;
            case 'L':
                snakeX[0] -= unitSize;
                break;
            case 'U':
                snakeY[0] -= unitSize;
                break;

            case 'D':
                snakeY[0] += unitSize;
                break;
        }
    }

    /**
     * Checks if the snake has eaten the apple.
     * If so, the snake grows and a new apple is created.
     */
    public void checkApple() {
        if (appleX == snakeX[0] && appleY == snakeY[0]) {
            growSnake();
            updateDelay();
            score++;
            createApple();
        }
    }

    /**
     * Grows the snake by increasing its length.
     */
    private void growSnake() {
        snakeLength++;
        snakeX = Arrays.copyOf(snakeX, snakeLength);
        snakeY = Arrays.copyOf(snakeY, snakeLength);
        snakeX[snakeLength - 1] = snakeX[snakeLength - 2];
        snakeY[snakeLength - 1] = snakeY[snakeLength - 2];
    }

    /**
     * Updates the delay based on the current delay step.
     * If a timer is active, the timer's delay is also updated.
     */
    private void updateDelay() {
        delay -= delayStep;
        if (timer != null) {
            timer.setDelay(delay);
        }
    }

    /**
     * Sets the difficulty level and updates the delay step accordingly.
     *
     * @param difficulty the difficulty level as a {@link String}
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty.toUpperCase();
        setDelay();
    }

    /**
     * Checks if the snake has collided with the wall or itself.
     * If so, the game stops.
     */
    public void checkCollision() {
        if (isCollision()) {
            isRunning = false;
            stopTimer();
        }
    }

    /**
     * Checks for collisions with the screen boundaries, the snake's body, or obstacles.
     *
     * @return {@code true} if a collision is detected, {@code false} otherwise.
     */
    private boolean isCollision() {
        // Check Screen Collision
        if (snakeX[0] >= screenProperties.get("SCREEN_WIDTH") || snakeX[0] < 0 || snakeY[0] >= screenProperties.get("SCREEN_HEIGHT") || snakeY[0] < 0)
            return true;

        // Check Body Collision
        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]) return true;
        }

        // Check Obstacles Collision
        return checkObstacleCollision(snakeX[0], snakeY[0]);
    }

    /**
     * Checks if a point at the specified coordinates collides with any obstacles.
     *
     * @param x The x-coordinate of the point to check.
     * @param y The y-coordinate of the point to check.
     * @return {@code true} if the point collides with any obstacle, {@code false} otherwise.
     */
    private boolean checkObstacleCollision(int x, int y) {
        if (!obstacles.isEmpty()) {
            for (ArrayList<Point> obstacle : obstacles) {
                for (Point point : obstacle) {
                    if (x == point.x && y == point.y) return true;
                }
            }
        }
        return false;
    }

    /**
     * Stops the game timer.
     */
    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    /**
     * Sets the delay step based on the current difficulty level.
     */
    public void setDelay() {
        delayStep = DIFFICULTIES_TYPES.get(this.difficulty);
    }
}
