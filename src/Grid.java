import java.awt.*;
import java.awt.event.KeyEvent;

public class Grid {
    private int gridWidth;
    private int gridHeight;
    private int blockSize;
    private int gridSize;
    private Bike b1;
    private Bike b2;

    public Grid(int x, int y, int size, int g) {
        gridWidth = x;
        gridHeight = y;
        blockSize = size;
        gridSize = g;
        b1 = new Bike(Color.red, blockSize, g, blockSize * 2, gridHeight / 2);
        b2 = new Bike(Color.blue, blockSize, g, gridWidth - 3 * blockSize, gridHeight / 2);
    }

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

    public void moveBikes(Direction d1, Direction d2) {
        b1.move(d1);
        b2.move(d2);
        checkCollision(b1, b2);
        checkCollision(b2, b2);
    }

    public boolean checkCollision(Bike b1, Bike b2) {
        for (int i = b1.getBikeLength(); i > 0; i--) {

            // upper side check
            if (b1.getBikePosY(0) < 0) {
                return true;
            }

            // bottom side check
            if (b1.getBikePosY(0) > gridHeight) {
                return true;
            }

            // right side check
            if (b1.getBikePosX(0) > gridWidth) {
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

    public Bike getBike1() {
        return b1;
    }

    public Bike getBike2() {
        return b2;
    }
}
