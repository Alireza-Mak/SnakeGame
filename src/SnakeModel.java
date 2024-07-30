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

    public SnakeModel() {
        rand = new Random();
        this.snakeLength = 4;
        this.snakeX = new int[snakeLength];
        this.snakeY = new int[snakeLength];
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

    public void setScreenDetails(Map<String, Integer> screenProperties) {
        this.screenProperties = screenProperties;
    }

    public void startGame() {
        createApple();
    }

    private void createApple() {
        appleX = rand.nextInt(screenProperties.get("SCREEN_WIDTH") / screenProperties.get("UNIT_SIZE")) * screenProperties.get("UNIT_SIZE");
        appleY = rand.nextInt(screenProperties.get("SCREEN_HEIGHT") / screenProperties.get("UNIT_SIZE")) * screenProperties.get("UNIT_SIZE");
    }


}
