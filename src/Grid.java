import java.awt.*;

/**
 * A class to handle the game grid.
 * It has a width, a height and a size.
 */
public class Grid {
    private final int gridWidth;
    private final int gridHeight;
    private final int blockSize;
    private final Bike b1;
    private final Bike b2;

    /**
     * Constructor for class Grid.
     * It also adds two bikes to the grid.
     * @param x width of the game in pixels
     * @param y height of the game in pixels
     * @param size size of a unit in pixels
     * @param gridSize number of units in the grid
     */
    public Grid(int x, int y, int size, int gridSize) {
        gridWidth = x;
        gridHeight = y;
        blockSize = size;
        b1 = new Bike(Color.red, blockSize, gridSize, blockSize * 2, gridHeight / 2);
        b2 = new Bike(Color.blue, blockSize, gridSize, gridWidth - 3 * blockSize, gridHeight / 2);
    }

    /**
     * Draws a grid with AWT.
     * Also calls drawing function of bikes.
     * @param g Graphics object to draw
     */
    public void drawGrid(Graphics g) {
        for (int i = 0; i < gridHeight / blockSize; i++) {
            g.drawLine(0, i * blockSize, gridWidth, i * blockSize);
        }
        for (int i = 0; i < gridWidth / blockSize; i++) {
            g.drawLine(i * blockSize, 0, i * blockSize, gridHeight);
        }
        b1.drawBike(g);
        b2.drawBike(g);
    }

    /**
     * Calls move function of the bikes and calls the collision checker functions.
     * @param d1 direction, where the first bike should move
     * @param d2 direction, where the second bike should move
     */
    public void moveBikes(Direction d1, Direction d2) {
        b1.move(d1);
        b2.move(d2);
        checkCollision(b1, b2);
        checkCollision(b2, b2);
    }

    /**
     * Checks the collision of the bikes.
     * Checks, if a bike hits the wall, another bike, or itself.
     * @param b1 first bike to check
     * @param b2 second bike to check
     * @return true, if bikes collide
     */
    public boolean checkCollision(Bike b1, Bike b2) {
        for (int i = b1.getBikeLength(); i > 0; i--) {

            // upper side check
            if (b1.getBikePosY(0) < 0) {
                return true;
            }

            // bottom side check
            if (b1.getBikePosY(0) > (gridHeight - blockSize)) {
                return true;
            }

            // right side check
            if (b1.getBikePosX(0) > (gridWidth - blockSize)) {
                return true;
            }

            // left side check
            if (b1.getBikePosX(0) < 0) {
                return true;
            }

            // hit itself
            if ((b1.getBikePosX(0) == b1.getBikePosX(i)) && (b1.getBikePosY(0) == b1.getBikePosY(i))) {
                return true;
            }

            // hit other bike
            if ((b1.getBikePosX(0) == b2.getBikePosX(i)) && (b1.getBikePosY(0) == b2.getBikePosY(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the first bike.
     * @return first bike
     */
    public Bike getBike1() {
        return b1;
    }

    /**
     * Returns the second bike.
     * @return second bike
     */
    public Bike getBike2() {
        return b2;
    }
}
