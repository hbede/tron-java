import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class BikeGameTests {
    Bike testBike;

    // testing in 10x10
    @BeforeEach
    void setup() {
        testBike = new Bike(Color.red, 1, 10, 2, 5);
    }

    // testing Bike.move()
    @Test
    void Given_RightDirection_When_BikeMoves_Then_BikeMovesInDirection() {
        int posTmp = testBike.getBikePosX(0);
        testBike.move(Direction.RIGHT);
        Assertions.assertEquals(testBike.getBikePosX(0), posTmp + 1);
    }

    @Test
    void Given_LeftDirection_When_BikeMoves_Then_BikeMovesInDirection() {
        int posTmp = testBike.getBikePosX(0);
        testBike.move(Direction.LEFT);
        Assertions.assertEquals(testBike.getBikePosX(0), posTmp - 1);
    }

    @Test
    void Given_UpDirection_When_BikeMoves_Then_BikeMovesInDirection() {
        int posTmp = testBike.getBikePosY(0);
        testBike.move(Direction.UP);
        Assertions.assertEquals(testBike.getBikePosY(0), posTmp - 1);
    }

    @Test
    void Given_DownDirection_When_BikeMoves_Then_BikeMovesInDirection() {
        int posTmp = testBike.getBikePosY(0);
        testBike.move(Direction.DOWN);
        Assertions.assertEquals(testBike.getBikePosY(0), posTmp + 1);
    }
}
