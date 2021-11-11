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
        b1 = new Bike(Color.red, blockSize, g, blockSize, gridHeight / 2);
        b2 = new Bike(Color.blue, blockSize, g, gridWidth - 2 * blockSize, gridHeight/2);
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
    }
}
