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
    public void testGenerateObstacle() {
        ArrayList<Point> generatedObstacle = obstacle.generateObstacle();
        assertNotNull(generatedObstacle);
        assertFalse(obstacle.isOutOfScreen(generatedObstacle));
    }

    @Test
    public void testCreteVectorsTriangle() {
        Point base = new Point(100, 100);
        ArrayList<Point> triangle = obstacle.creteVectors(base, "triangle");

        assertEquals(3, triangle.size());
        assertEquals(new Point(100, 100), triangle.get(0));
        assertEquals(new Point(120, 120), triangle.get(1));
        assertEquals(new Point(80, 120), triangle.get(2));
    }

    @Test
    public void testCreteVectorsRectangle() {
        Point base = new Point(100, 100);
        ArrayList<Point> rectangle = obstacle.creteVectors(base, "rectangle");

        assertEquals(6, rectangle.size());
        assertEquals(new Point(100, 100), rectangle.get(0));
        assertEquals(new Point(120, 100), rectangle.get(1));
        assertEquals(new Point(140, 100), rectangle.get(2));
        assertEquals(new Point(100, 120), rectangle.get(3));
        assertEquals(new Point(120, 120), rectangle.get(4));
        assertEquals(new Point(140, 120), rectangle.get(5));
    }

    @Test
    public void testCreteVectorsSquare() {
        Point base = new Point(100, 100);
        ArrayList<Point> square = obstacle.creteVectors(base, "square");

        assertEquals(4, square.size());
        assertEquals(new Point(100, 100), square.get(0));
        assertEquals(new Point(120, 100), square.get(1));
        assertEquals(new Point(120, 120), square.get(2));
        assertEquals(new Point(100, 120), square.get(3));
    }

    @Test
    public void testCreteVectorsInvalidType() {
        Point base = new Point(100, 100);
        assertThrows(IllegalStateException.class, () -> obstacle.creteVectors(base, "invalidType"));
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
        inScreen.add(new Point(screenWidth - 1, screenHeight - 1)); // within bounds
        assertFalse(obstacle.isOutOfScreen(inScreen));

        ArrayList<Point> outScreen = new ArrayList<>();

        outScreen = new ArrayList<>();
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
    public void testGenerateObstacleWithRecursion() {
        // To force recursion, we create an obstacle that is likely out of bounds
        Obstacle obstacleStub = new Obstacle(40, 40, 20); // smaller screen size to force out-of-bounds
        ArrayList<Point> generatedObstacle = obstacleStub.generateObstacle();

        // The final obstacle should still be within bounds
        assertFalse(obstacleStub.isOutOfScreen(generatedObstacle));
    }
}
