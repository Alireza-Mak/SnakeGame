public class SnakeGame {
    public static void main(String[] args) {
        SnakeView snakeView = new SnakeView();
        SnakeModel snakeModel = new SnakeModel();
        new SnakeController(snakeView, snakeModel);

    }
}
