import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;


public class SnakeModel {
    private final Random rand;
    Map<String, Integer> screenProperties;
    private int appleX;
    private int appleY;
    private int snakeLength;
    private int[] snakeX;
    private int[] snakeY;
    private char direction = 'R';
    private Timer timer;
    private int delay = 100;
    private int score;

    public SnakeModel() {
        rand = new Random();
        this.snakeLength = 4;
        this.snakeX = new int[snakeLength];
        this.snakeY = new int[snakeLength];
    }

    public void setScreenDetails(Map<String, Integer> screenProperties) {
        this.screenProperties = screenProperties;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }

    public int getScore() { return score; }

    public int[] getSnakeX() {
        return snakeX;
    }

    public int[] getSnakeY() {
        return snakeY;
    }

    public int getAppleY() {
        return appleY;
    }

    public int getAppleX() {
        return appleX;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public void startGame(ActionListener actionListener) {
        timer = new Timer(delay, actionListener);
        score =0;
        timer.start();
        createApple();
    }

    private void createApple() {
        appleX = rand.nextInt(screenProperties.get("SCREEN_WIDTH") / screenProperties.get("UNIT_SIZE")) * screenProperties.get("UNIT_SIZE");
        appleY = rand.nextInt(screenProperties.get("SCREEN_HEIGHT") / screenProperties.get("UNIT_SIZE")) * screenProperties.get("UNIT_SIZE");
    }

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

    public void checkApple() {
        if (appleX == snakeX[0] && appleY == snakeY[0]) {
            snakeLength++;
            snakeX = Arrays.copyOf(snakeX, snakeLength);
            snakeY = Arrays.copyOf(snakeY, snakeLength);
            snakeX[snakeLength - 1] = snakeX[snakeLength - 2];
            snakeY[snakeLength - 1] = snakeY[snakeLength - 2];
            delay -= 10;
            score++;
            timer.setDelay(delay);
            createApple();
        }
    }
}
