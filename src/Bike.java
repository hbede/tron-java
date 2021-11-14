import java.awt.*;

public class Bike {
    private Color color;
    private int width;
    private int grid;
    private int[] x;
    private int[] y;
    private int length = 1;

    public Bike(Color c, int w, int g, int s1, int s2) {
        color = c;
        width = w;
        grid = g;
        x = new int[grid];
        y = new int[grid];
        x[0] = s1;
        y[0] = s2;
    }

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

    public void drawBike(Graphics g) {
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                g.setColor(color);
                g.fillRect(x[i], y[i], width, width);
            } else {
                g.setColor(color);
                g.fillRect(x[i], y[i], width, width);
            }

            // ekkor még csak a fej jelenik meg, meg kell hívni az action performeddel
        }
    }

    public int getBikeLength() {
        return length;
    }

    public int getBikePosX(int i) {
        return x[i];
    }

    public int getBikePosY(int i) {
        return y[i];
    }

}
