package Components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    private Obstacle obstacle;
    private final int screenWidth = 800;
    private final int screenHeight = 600;
    private final int obstacleSize = 20;

    @BeforeEach
    public void setUp() {
        obstacle = new Obstacle(screenWidth, screenHeight, obstacleSize);
    }

    @Test
    public void testGeneratePoints() {
        ArrayList<Point> generatedObstacle = obstacle.generatePoints();
        assertNotNull(generatedObstacle);
        assertFalse(obstacle.isOutOfScreen(generatedObstacle));
    }

    @Test
    public void testCreatePointsTriangle() {
        Point base = new Point(100, 100);
        ArrayList<Point> triangle = obstacle.createPoints(base, "triangle");

        assertEquals(3, triangle.size());
        assertEquals(new Point(100, 100), triangle.get(0));
        assertEquals(new Point(120, 120), triangle.get(1));
        assertEquals(new Point(80, 120), triangle.get(2));
    }

    @Test
    public void testCreatePointsRectangle() {
        Point base = new Point(100, 100);
        ArrayList<Point> rectangle = obstacle.createPoints(base, "rectangle");

        assertEquals(6, rectangle.size());
        assertEquals(new Point(100, 100), rectangle.get(0));
        assertEquals(new Point(120, 100), rectangle.get(1));
        assertEquals(new Point(140, 100), rectangle.get(2));
        assertEquals(new Point(100, 120), rectangle.get(3));
        assertEquals(new Point(120, 120), rectangle.get(4));
        assertEquals(new Point(140, 120), rectangle.get(5));
    }

    @Test
    public void testCreatePointsSquare() {
        Point base = new Point(100, 100);
        ArrayList<Point> square = obstacle.createPoints(base, "square");

        assertEquals(4, square.size());
        assertEquals(new Point(100, 100), square.get(0));
        assertEquals(new Point(120, 100), square.get(1));
        assertEquals(new Point(120, 120), square.get(2));
        assertEquals(new Point(100, 120), square.get(3));
    }

    @Test
    public void testCreatePointsInvalidType() {
        Point base = new Point(100, 100);
        assertThrows(IllegalStateException.class, () -> obstacle.createPoints(base, "invalidType"));
    }

    @Test
    public void testGenerateRandomPoint() {
        Point point = obstacle.generateRandomPoint();
        assertTrue(point.x >= 0 && point.x < screenWidth);
        assertTrue(point.y >= 0 && point.y < screenHeight);

        point = obstacle.generateRandomPoint();
        assertEquals(0, point.x % obstacleSize);
        assertEquals(0, point.y % obstacleSize);

        for (int i = 0; i < 1000; i++) { // Test 1000 points
            point = obstacle.generateRandomPoint();
            assertTrue(point.x >= 0 && point.x < screenWidth);
            assertTrue(point.y >= 0 && point.y < screenHeight);
            assertEquals(0, point.x % obstacleSize);
            assertEquals(0, point.y % obstacleSize);
        }

    }

    @Test
    public void testIsOutOfScreen() {
        ArrayList<Point> inScreen = new ArrayList<>();
        inScreen.add(new Point(100, 100));
        inScreen.add(new Point(120, 100));
        inScreen.add(new Point(140, 100));
        assertFalse(obstacle.isOutOfScreen(inScreen));

        inScreen = new ArrayList<>();
        inScreen.add(new Point(screenWidth - obstacleSize - 1, screenHeight - obstacleSize - 1)); // within bounds
        assertFalse(obstacle.isOutOfScreen(inScreen));

        ArrayList<Point> outScreen = new ArrayList<>();
        outScreen.add(new Point(screenWidth + 1, 100)); // x is equal to screenWidth, out of bounds
        assertTrue(obstacle.isOutOfScreen(outScreen));

        outScreen = new ArrayList<>();
        outScreen.add(new Point(100, -1)); // y is out of bounds
        assertTrue(obstacle.isOutOfScreen(outScreen));

        outScreen = new ArrayList<>();
        outScreen.add(new Point(100, screenHeight + 1)); // y is equal to screenHeight, out of bounds
        assertTrue(obstacle.isOutOfScreen(outScreen));

        outScreen = new ArrayList<>();
        outScreen.add(new Point(-1, 100)); // y is out of bounds
        assertTrue(obstacle.isOutOfScreen(outScreen));
    }

    @Test
    public void testGeneratePointsWithRecursion() {
        // To force recursion, we create an obstacle that is likely out of bounds
        Obstacle obstacleStub = new Obstacle(40, 40, 20); // smaller screen size to force out-of-bounds
        ArrayList<Point> generatedObstacle = obstacleStub.generatePoints();

        // The final obstacle should still be within bounds
        assertFalse(obstacleStub.isOutOfScreen(generatedObstacle));
    }

    @Test
    void testObstaclesGenerator() {
        // Check the Parameter
        int numberOfObstacles = 0;
        ArrayList<ArrayList<Point>> obstacles = obstacle.obstaclesGenerator(numberOfObstacles);
        ArrayList<ArrayList<Point>> expected = new ArrayList<>();
        assertEquals(expected, obstacles);

        // Check the Parameter
        numberOfObstacles = 3;
        obstacles = obstacle.obstaclesGenerator(numberOfObstacles);
        int expected2 = 3;
        assertEquals(expected2, obstacles.size());
    }


    @Test
    void tetObstaclesCollision() {
        ArrayList<Point> obs = new ArrayList<>() {{
            add(new Point(100, 100));
            add(new Point(120, 120));
            add(new Point(80, 120));
        }};
        boolean collisionObstacle = obstacle.obstaclesCollision(new ArrayList<>(), obs);
        assertFalse(collisionObstacle);

        ArrayList<ArrayList<Point>> obstacles = new ArrayList<>() {{
            add(new ArrayList<>() {{
                add(new Point(100, 100));
                add(new Point(120, 120));
                add(new Point(80, 120));
            }});
        }};
        collisionObstacle = obstacle.obstaclesCollision(obstacles, obs);
        assertTrue(collisionObstacle);

        obstacles = new ArrayList<>() {{
            add(new ArrayList<>() {{
                add(new Point(80, 100));
                add(new Point(120, 120));
                add(new Point(80, 120));
            }});
        }};
        collisionObstacle = obstacle.obstaclesCollision(obstacles, obs);

        assertTrue(collisionObstacle);

        obstacles = new ArrayList<>() {{
            add(new ArrayList<>() {{
                add(new Point(100, 120));
                add(new Point(120, 140));
                add(new Point(80, 140));
            }});
        }};
        collisionObstacle = obstacle.obstaclesCollision(obstacles, obs);

        assertFalse(collisionObstacle);
    }
}
