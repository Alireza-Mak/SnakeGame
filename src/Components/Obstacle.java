package Components;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Responsible for creating and placing obstacles on the game board.
 */
public class Obstacle {

    ArrayList<ArrayList<Point>> obstacles;

    private final int screenWidth;
    private final int screenHeight;
    private final int obstacleSize;
    private final Random random;
    private final String[] obstacleType = new String[]{"square", "rectangle", "triangle"};

    /**
     * Constructor to initialize the ObstacleCreator with screen dimensions.
     *
     * @param screenWidth  the width of the game screen.
     * @param screenHeight the height of the game screen.
     * @param obstacleSize the size of each obstacle.
     */
    public Obstacle(int screenWidth, int screenHeight, int obstacleSize) {
        random = new Random();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.obstacleSize = obstacleSize;
        this.obstacles = new ArrayList<>();
    }

    /**
     * Generates a list of points representing an obstacle with a random shape.
     * <p>
     * The method selects a random shape type and generates its vertices based on a randomly
     * generated base point. If the generated obstacle is out of screen bounds, the method
     * recursively generates a new obstacle.
     * </p>
     *
     * @return an {@link ArrayList} of {@link Point} objects representing the obstacle
     */
    public ArrayList<Point> generatePoints() {
        Point base = generateRandomPoint();
        int selectedIndex = random.nextInt(obstacleType.length);
        String selectedType = obstacleType[selectedIndex];
        ArrayList<Point> obstaclePoints = createPoints(base, selectedType);

        if (isOutOfScreen(obstaclePoints)) {
            return generatePoints();
        } else return obstaclePoints;
    }

    /**
     * Creates a list of points representing the vertices of a shape based on the selected type.
     * <p>
     * The shape type is specified by {@code selectedType} and determines the arrangement of points.
     * </p>
     *
     * @param base         the base {@link Point} for the shape
     * @param selectedType the type of shape ("triangle", "rectangle", "square")
     * @return an {@link ArrayList} of {@link Point} objects representing the shape's vertices
     * @throws IllegalStateException if {@code selectedType} is invalid
     */
    public ArrayList<Point> createPoints(Point base, String selectedType) {
        ArrayList<Point> obstacle = new ArrayList<>();
        obstacle.add(base);
        switch (selectedType) {
            case "triangle":
                obstacle.add(new Point(base.x + obstacleSize, base.y + obstacleSize));
                obstacle.add(new Point(base.x - obstacleSize, base.y + obstacleSize));
                break;
            case "rectangle":
                obstacle.add(new Point(base.x + obstacleSize, base.y));
                obstacle.add(new Point(base.x + (2 * obstacleSize), base.y));
                obstacle.add(new Point(base.x, base.y + obstacleSize));
                obstacle.add(new Point(base.x + obstacleSize, base.y + obstacleSize));
                obstacle.add(new Point(base.x + (2 * obstacleSize), base.y + obstacleSize));
                break;
            case "square":
                obstacle.add(new Point(base.x + obstacleSize, base.y));
                obstacle.add(new Point(base.x + obstacleSize, base.y + obstacleSize));
                obstacle.add(new Point(base.x, base.y + obstacleSize));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedType);
        }
        return obstacle;
    }

    /**
     * Generates a random point within the screen bounds, aligned to the obstacle size.
     *
     * @return a {@link Point} object representing the randomly generated point
     */
    public Point generateRandomPoint() {
        int y = random.nextInt(screenHeight / obstacleSize) * obstacleSize;
        int x = random.nextInt(screenWidth / obstacleSize) * obstacleSize;
        return new Point(x, y);
    }

    /**
     * Determines if any of the points in the given list are outside the screen bounds.
     *
     * @param obstacle a list of {@link Point} objects representing the obstacles to be checked
     * @return {@code true} if at least one point is outside the screen bounds; {@code false} otherwise
     */
    public boolean isOutOfScreen(ArrayList<Point> obstacle) {
        for (Point point : obstacle) {
            if (point.x < 0 || point.x > screenWidth - obstacleSize || point.y < 0 || point.y > screenHeight - obstacleSize) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a list of obstacles, avoiding collisions between them.
     *
     * @param numberOfObstacles The number of obstacles to generate.
     * @return A list of obstacles, each represented as a list of {@link Point} objects.
     */
    public ArrayList<ArrayList<Point>> obstaclesGenerator(int numberOfObstacles) {
        ArrayList<ArrayList<Point>> obstacles = new ArrayList<>();

        for (int i = 0; i < numberOfObstacles; i++) {
            ArrayList<Point> new_obstacle;
            do {
                new_obstacle = generatePoints();
            } while (obstaclesCollision(obstacles, new_obstacle));

            obstacles.add(new_obstacle);

        }
        return obstacles;
    }

    /**
     * Checks if a new obstacle collides with any existing obstacles.
     *
     * @param obstacles   The current obstacles as a list of {@link Point} lists.
     * @param newObstacle The new obstacle as a list of {@link Point} objects.
     * @return {@code true} if there is a collision, {@code false} otherwise.
     */
    public boolean obstaclesCollision(ArrayList<ArrayList<Point>> obstacles, ArrayList<Point> newObstacle) {
        if (obstacles.isEmpty()) {
            return false;
        }
        for (ArrayList<Point> obstacle : obstacles) {
            for (Point point : obstacle) {
                for (Point newPoint : newObstacle) {
                    if (point.x == newPoint.x && point.y == newPoint.y) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
