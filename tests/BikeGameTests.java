import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * Test class for game mechanics.
 */
public class BikeGameTests {
    Bike testBike1;
    Bike testBike2;

    // testing in 10x10
    @BeforeEach
    void setup() {
        testBike1 = new Bike(Color.red, 1, 10, 1, 5);
        testBike2 = new Bike(Color.red, 1, 10, 8, 5);
    }

    // testing Bike.move()
    @Test
    void Given_RightDirection_When_BikeMoves_Then_BikeMovesInDirection() {
        int posTmp = testBike1.getBikePosX(0);
        testBike1.move(Direction.RIGHT);
        Assertions.assertEquals(testBike1.getBikePosX(0), posTmp + 1);
    }

    @Test
    void Given_LeftDirection_When_BikeMoves_Then_BikeMovesInDirection() {
        int posTmp = testBike1.getBikePosX(0);
        testBike1.move(Direction.LEFT);
        Assertions.assertEquals(testBike1.getBikePosX(0), posTmp - 1);
    }

    @Test
    void Given_UpDirection_When_BikeMoves_Then_BikeMovesInDirection() {
        int posTmp = testBike1.getBikePosY(0);
        testBike1.move(Direction.UP);
        Assertions.assertEquals(testBike1.getBikePosY(0), posTmp - 1);
    }

    @Test
    void Given_DownDirection_When_BikeMoves_Then_BikeMovesInDirection() {
        int posTmp = testBike1.getBikePosY(0);
        testBike1.move(Direction.DOWN);
        Assertions.assertEquals(testBike1.getBikePosY(0), posTmp + 1);
    }

    // testing collision
    @Test
    void Given_BikeNearLeftWall_When_BikeMovesAndDirectionToWall_Then_ShouldCollide() {
        Grid grid = new Grid(10, 10, 1, 100);
        testBike1.move(Direction.UP);
        testBike1.move(Direction.LEFT);
        testBike1.move(Direction.LEFT);
        Assertions.assertTrue(grid.checkCollision(testBike1, testBike2));
    }

    @Test
    void Given_BikeNearUpperWall_When_BikeMovesAndDirectionToWall_Then_ShouldCollide() {
        Grid grid = new Grid(10, 10, 1, 100);
        int posY= testBike1.getBikePosY(0);
        for (int i = 0; i <= posY; i++) {
            testBike1.move(Direction.UP);
        }

        Assertions.assertTrue(grid.checkCollision(testBike1, testBike2));
    }

    @Test
    void Given_BikeNearBottomWall_When_BikeMovesAndDirectionToWall_Then_ShouldCollide() {
        Grid grid = new Grid(10, 10, 1, 100);
        int posY= testBike1.getBikePosY(0);
        for (int i = 0; i <= 10 - posY; i++) {
            testBike1.move(Direction.DOWN);
        }

        Assertions.assertTrue(grid.checkCollision(testBike1, testBike2));
    }

    @Test
    void Given_BikeNearRightWall_When_BikeMovesAndDirectionToWall_Then_ShouldCollide() {
        Grid grid = new Grid(10, 10, 1, 100);
        testBike2.move(Direction.UP);
        testBike2.move(Direction.RIGHT);
        testBike2.move(Direction.RIGHT);
        testBike2.move(Direction.RIGHT);

        Assertions.assertTrue(grid.checkCollision(testBike1, testBike2));
    }
    @Test
    void Given_BikeNearEachOther_When_BikeMovesAndDirectionToEachOther_Then_ShouldCollide() {
        Grid grid = new Grid(10, 10, 1, 100);
        testBike1.move(Direction.RIGHT);
        testBike1.move(Direction.RIGHT);
        testBike1.move(Direction.RIGHT);
        testBike1.move(Direction.RIGHT);
        testBike1.move(Direction.RIGHT);

        testBike2.move(Direction.LEFT);
        testBike2.move(Direction.LEFT);
        testBike2.move(Direction.LEFT);
        testBike2.move(Direction.LEFT);
        testBike2.move(Direction.LEFT);

        Assertions.assertTrue(grid.checkCollision(testBike1, testBike2));
    }

    @Test
    void Given_Bike_When_BikeMovesIntoItself_Then_ShouldCollide() {
        Grid grid = new Grid(10, 10, 1, 100);
        testBike1.move(Direction.LEFT);
        testBike1.move(Direction.UP);
        testBike1.move(Direction.RIGHT);
        testBike1.move(Direction.DOWN);

        Assertions.assertTrue(grid.checkCollision(testBike1, testBike2));
    }
}
