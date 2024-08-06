/**
 * The SnakeGame class serves as the entry point for the Snake game application.
 * It initializes the game components, including the view, model, and controller.
 *
 * @author Alireza Mak
 * @version 1.0
 * @since 2024-08-06
 */
public class SnakeGame {
    /**
     * The main method is the entry point for the Snake game application.
     * It sets up the game by creating instances of SnakeView, SnakeModel,
     * and SnakeController, which manage the game's user interface, logic, and control flow.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create the game view which handles the graphical interface and user interactions.
        SnakeView snakeView = new SnakeView();

        // Create the game model which manages the game state and logic.
        SnakeModel snakeModel = new SnakeModel();

        // Create the game controller which links the view and model, and handles user inputs.
        new SnakeController(snakeView, snakeModel);
    }
}
