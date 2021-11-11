import java.awt.event.KeyEvent;

public class Controls {
    private int p1Up, p1Down, p1Right, p1Left;
    private int p2Up, p2Down, p2Right, p2Left;
    private int menu = KeyEvent.VK_ESCAPE;

    void setP1Keys(int key1, int key2, int key3, int key4) {
        p1Up = key1;
        p1Down = key2;
        p1Right = key3;
        p1Left = key4;
    }

    void setP2Keys(int key1, int key2, int key3, int key4) {
        p2Up = key1;
        p2Down = key2;
        p2Right = key3;
        p2Left = key4;
    }

    public int getP1Up() {
        return p1Up;
    }

    public int getP1Down() {
        return p1Down;
    }

    public int getP1Right() {
        return p1Right;
    }

    public int getP1Left() {
        return p1Left;
    }

    public int getP2Up() {
        return p2Up;
    }

    public int getP2Down() {
        return p2Down;
    }

    public int getP2Right() {
        return p2Right;
    }

    public int getP2Left() {
        return p2Left;
    }

    public int getMenu() {
        return menu;
    }
}
