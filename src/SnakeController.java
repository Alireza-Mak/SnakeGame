import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        this.snakeView.addKeyListener(new MyKeyListener());
    }

    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (snakeModel.getTimer().isRunning()) snakeModel.getTimer().stop();
                snakeView.exitConfirmation();

            }
            snakeModel.getTimer().start();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (snakeModel.getDirection() != 'D') snakeModel.setDirection('U');
                    break;
                case KeyEvent.VK_DOWN:
                    if (snakeModel.getDirection() != 'U') snakeModel.setDirection('D');
                    break;

                case KeyEvent.VK_RIGHT:
                    if (snakeModel.getDirection() != 'L') snakeModel.setDirection('R');
                    break;
                case KeyEvent.VK_LEFT:
                    if (snakeModel.getDirection() != 'R') snakeModel.setDirection('L');
                    break;
            }
        }
    }

    private class SnakeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            snakeModel.moveSnake(snakeView.getScreenProperties().get("UNIT_SIZE"));
            snakeView.repaint();
        }
    }
}