public class SnakeController {
    private final SnakeModel snakeModel;
    private final SnakeView snakeView;

    public SnakeController(SnakeView snakeView, SnakeModel snakeModel) {
        this.snakeView = snakeView;
        this.snakeModel = snakeModel;
    }
}