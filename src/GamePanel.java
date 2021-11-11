import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    private static int RES_X = 600;
    private static int RES_Y = 600;
    private static int UNIT_SIZE = 10;
    private static int DELAY = 75;
    static final int GAME_UNITS = (RES_X * RES_Y) / UNIT_SIZE;
    private boolean isRunning = false;
    private Grid grid;
    private Timer timer;
    private Direction direction1 = Direction.RIGHT, direction2 = Direction.LEFT;
    private Controls controls = new Controls();

    public GamePanel() {
        setPreferredSize(new Dimension(RES_X, RES_Y));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new TronKeyAdapter()); // nem ide kéne! valahogy a gridbe vagy a bikeba superrel
        start();
    }

    public void start() {
        isRunning = true;
        grid = new Grid(RES_X, RES_Y, UNIT_SIZE, GAME_UNITS);
        timer = new Timer(DELAY, this);
        timer.start();
        controls.setP1Keys(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_A);
        controls.setP2Keys(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        grid.drawGrid(g);
    }

    public void end() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            grid.moveBikes(direction1, direction2);
        }
        repaint();
    }

    class TronKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) { // ötlet: gamepanelbe switch case mind a 8 gombra, a gombok valahogy a bikeoknak a dolgait
            // p1 keys
            if (e.getKeyCode() == controls.getP1Right() && direction1 != Direction.LEFT) {
                direction1 = Direction.RIGHT;
            }
            else if (e.getKeyCode() == controls.getP1Left() && direction1 != Direction.RIGHT) {
                direction1 = Direction.LEFT;
            }
            else if (e.getKeyCode() == controls.getP1Up() && direction1 != Direction.DOWN) {
                direction1 = Direction.UP;
            }
            else if (e.getKeyCode() == controls.getP1Down() && direction1 != Direction.UP) {
                direction1 = Direction.DOWN;
            }
            // p2 keys
            if (e.getKeyCode() == controls.getP2Right() && direction2 != Direction.LEFT) {
                direction2 = Direction.RIGHT;
            }
            else if (e.getKeyCode() == controls.getP2Left() && direction2 != Direction.RIGHT) {
                direction2 = Direction.LEFT;
            }
            else if (e.getKeyCode() == controls.getP2Up() && direction2 != Direction.DOWN) {
                direction2 = Direction.UP;
            }
            else if (e.getKeyCode() == controls.getP2Down() && direction2 != Direction.UP) {
                direction2 = Direction.DOWN;
            }
        }
    }

}
