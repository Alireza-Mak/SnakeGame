import java.awt.event.*;

/**
 * The SnakeController class handles the interaction between the SnakeModel and the SnakeView.
 * It initializes the game and sets up listeners for user input.
 *
 * @author Alireza Mak
 * @version 1.0
 * @since 2024-08-06
 */

public class SnakeController {
    private final SnakeModel snakeModel;
    private final SnakeView snakeView;

    /**
     * Constructor to initialize the SnakeController with the view and model.
     *
     * @param snakeView  The view component of the game.
     * @param snakeModel The model component of the game.
     */
    public SnakeController(SnakeView snakeView, SnakeModel snakeModel) {
        this.snakeView = snakeView;
        this.snakeModel = snakeModel;

        // Initialize the model and view
        initializeGame();
        this.snakeView.addKeyListener(new SnakeGameKeyListener());
        this.snakeView.addResetButtonListener(new SnakeGameMouseListener());
        this.snakeView.addMenuDisableGridListener(new AddMenuDisableGridListener());
        this.snakeView.addMenuEnableGridListener(new AddMenuEnableGridListener());
        this.snakeView.addMenuQuitListener(new AddMenuQuitListener());
    }

    private void initializeGame() {
        this.snakeModel.setScreenDetails(this.snakeView.getScreenProperties());
        startGame();
    }

    private void startGame() {
        this.snakeModel.startGame(new SnakeActionListener());
        updateView();
    }

    /**
     * Updates the view with the current state of the model.
     */
    private void updateView() {
        this.snakeView.setAppleX(this.snakeModel.getAppleX());
        this.snakeView.setAppleY(this.snakeModel.getAppleY());
        this.snakeView.setSnakeX(this.snakeModel.getSnakeX());
        this.snakeView.setSnakeY(this.snakeModel.getSnakeY());
        this.snakeView.setScore(this.snakeModel.getScore());
        this.snakeView.setIsRunning(this.snakeModel.getIsRunning());
        this.snakeView.repaint();
    }

    /**
     * Mouse listener for the reset button.
     */
    private class SnakeGameMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            startGame();
            snakeView.getResetButton().setVisible(false);
        }
    }

    /**
     * Key listener for controlling the snake.
     */
    private class SnakeGameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (snakeModel.getTimer() != null) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    handleEscapeKey();
                }
                handleDirectionKey(e.getKeyCode());
            }
        }

        /**
         * Updates the view with the current state of the model.
         */
        private void handleDirectionKey(int keyCode) {
            snakeModel.getTimer().start();
            switch (keyCode) {
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

        /**
         * Handles the action when the "Escape" key is pressed.
         * Stops the game timer if it is running and displays an exit confirmation dialog.
         */

        private void handleEscapeKey() {
            if (snakeModel.getTimer().isRunning()) {
                snakeModel.getTimer().stop();
            }
            snakeView.showExitConfirmation();
        }
    }

    /**
     * Action listener for the game timer.
     */
    private class SnakeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            snakeModel.moveSnake(snakeView.getScreenProperties().get("UNIT_SIZE"));
            snakeModel.checkApple();
            snakeModel.checkCollision();
            updateView();
        }
    }

    /**
     * Handles the action event when the "Quit" menu item is selected.
     * Stops the game timer, shows an exit confirmation dialog, and resumes the timer if not exiting.
     */
    private class AddMenuQuitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (snakeModel.getTimer() != null) {
                if (snakeModel.getTimer().isRunning()) {
                    snakeModel.getTimer().stop();
                    snakeView.showExitConfirmation();
                }
                snakeModel.getTimer().start();
            } else {
                snakeView.showExitConfirmation();
            }
        }
    }

    /**
     * Handles the action event when the "Enable Grid" menu item is selected.
     * Enables the grid display on the game board and repaints the view.
     */
    private class AddMenuEnableGridListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            snakeView.setShowingGrid(true);
            snakeView.repaint();
        }
    }

    /**
     * Handles the action event when the "Disable Grid" menu item is selected.
     * Disables the grid display on the game board and repaints the view.
     */
    private class AddMenuDisableGridListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            snakeView.setShowingGrid(false);
            snakeView.repaint();
        }
    }
}