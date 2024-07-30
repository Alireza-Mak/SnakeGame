import java.awt.event.*;

public class SnakeController {
    private final SnakeModel snakeModel;
    private final SnakeView snakeView;

    public SnakeController(SnakeView snakeView, SnakeModel snakeModel) {
        this.snakeView = snakeView;
        this.snakeModel = snakeModel;

        this.snakeModel.setScreenDetails(this.snakeView.getScreenProperties());
        this.snakeModel.startGame(new SnakeActionListener());
        this.snakeView.setAppleX(this.snakeModel.getAppleX());
        this.snakeView.setAppleY(this.snakeModel.getAppleY());
        this.snakeView.setSnakeX(this.snakeModel.getSnakeX());
        this.snakeView.setSnakeY(this.snakeModel.getSnakeY());
    }


    private class SnakeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            snakeModel.moveSnake(snakeView.getScreenProperties().get("UNIT_SIZE"));
            snakeView.repaint();
        }
    }
}