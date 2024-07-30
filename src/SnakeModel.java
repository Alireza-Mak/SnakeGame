import java.awt.event.*;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

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


    public void startGame(ActionListener actionListener) {
        timer = new Timer(delay, actionListener);
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
}
