public class SnakeController {
    private final SnakeModel snakeModel;
    private final SnakeView snakeView;

    public SnakeController(SnakeView snakeView, SnakeModel snakeModel) {
        this.snakeView = snakeView;
        this.snakeModel = snakeModel;

        this.snakeModel.setScreenDetails(this.snakeView.getScreenProperties());
        this.snakeModel.startGame();
        this.snakeView.setAppleX(this.snakeModel.getAppleX());
        this.snakeView.setAppleY(this.snakeModel.getAppleY());
        this.snakeView.setSnakeX(this.snakeModel.getSnakeX());
        this.snakeView.setSnakeY(this.snakeModel.getSnakeY());
    }
}