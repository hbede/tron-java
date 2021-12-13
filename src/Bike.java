import java.awt.*;

/**
 * This class handles and stores information of a bike object.
 * These include color, position, width, length etc.
 * The class also handles movement and collision.
 */
public class Bike {
    private final Color color;
    private final int width;
    private final int[] x;
    private final int[] y;
    private int length = 1;

    /**
     * Constructor for class Bike, initialized with the parameters of class Grid.
     * @param c the color of bike
     * @param w width of bike
     * @param g width of grid in blocks
     * @param s1 initial position on axis x
     * @param s2 initial position on axis y
     */
    public Bike(Color c, int w, int g, int s1, int s2) {
        color = c;
        width = w;
        x = new int[g];
        y = new int[g];
        x[0] = s1;
        y[0] = s2;
    }

    /**
     * Moves the bike in the provided direction.
     * @param dir direction, to tell the bike should it move
     */
    public void move(Direction dir) {
        for (int i = length++; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (dir) {
            case UP -> y[0] = y[0] - width;
            case DOWN -> y[0] = y[0] + width;
            case LEFT -> x[0] = x[0] - width;
            case RIGHT -> x[0] = x[0] + width;
        }
    }

    /**
     * This function draws bike using AWT.
     * @param g an input Graphics object
     */
    public void drawBike(Graphics g) {
        for (int i = 0; i < length; i++) {
            g.setColor(color);
            g.fillRect(x[i], y[i], width, width);
        }
    }

    /**
     * Returns actual length of bike.
     * @return the actual length of bike
     */
    public int getBikeLength() {
        return length;
    }

    /**
     * Returns position on the x-axis of the i-th segment of bike.
     * @param i index of bike segment
     * @return position of i-th bike segment on the x-axis
     */
    public int getBikePosX(int i) {
        return x[i];
    }

    /**
     * Returns position on the y-axis of the i-th segment of bike.
     * @param i index of bike segment
     * @return position of i-th bike segment on the y-axis
     */
    public int getBikePosY(int i) {
        return y[i];
    }

}
