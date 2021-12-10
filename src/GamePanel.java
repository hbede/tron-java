import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

public class GamePanel extends JPanel implements ActionListener {
    private static int RES_X = 1500;
    private static int RES_Y = 800;
    private static int UNIT_SIZE = 10;
    private static int DELAY = 50;
    static final int GAME_UNITS = (RES_X * RES_Y) / UNIT_SIZE;
    boolean isRunning = false;
    private Grid grid;
    private Timer timer;
    private Direction direction1 = Direction.RIGHT, direction2 = Direction.LEFT;
    private Controls controls = new Controls();
    boolean p1Lost = false;
    boolean p2Lost = false;

    private enum STATE{
        MENU,
        GAME
    };

    private STATE State = STATE.MENU;

    Action upAction1;
    Action downAction1;
    Action rightAction1;
    Action leftAction1;

    Action upAction2;
    Action downAction2;
    Action rightAction2;
    Action leftAction2;

    public GamePanel() {
        setPreferredSize(new Dimension(RES_X, RES_Y));
        setBackground(Color.black);

        upAction1 = new UpAction1();
        downAction1 = new DownAction1();
        rightAction1 = new RightAction1();
        leftAction1 = new LeftAction1();

        upAction2 = new UpAction2();
        downAction2 = new DownAction2();
        rightAction2 = new RightAction2();
        leftAction2 = new LeftAction2();

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('w'), "upAction1");
        this.getActionMap().put("upAction1", upAction1);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'), "downAction1");
        this.getActionMap().put("downAction1", downAction1);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('d'), "rightAction1");
        this.getActionMap().put("rightAction1", rightAction1);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('a'), "leftAction1");
        this.getActionMap().put("leftAction1", leftAction1);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "upAction2");
        this.getActionMap().put("upAction2", upAction2);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downAction2");
        this.getActionMap().put("downAction2", downAction2);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "rightAction2");
        this.getActionMap().put("rightAction2", rightAction2);

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "leftAction2");
        this.getActionMap().put("leftAction2", leftAction2);


        setFocusable(true);
    }

    public void start() {
        isRunning = true;
        grid = new Grid(RES_X, RES_Y, UNIT_SIZE, GAME_UNITS);
        timer = new Timer(DELAY, this);
        direction1 = Direction.RIGHT;
        direction2 = Direction.LEFT;
        timer.start();
        //controls.setP1Keys(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_A);
        //controls.setP2Keys(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        grid.drawGrid(g);
        if (p1Lost && !p2Lost) {
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Blue won!", (RES_X - metrics2.stringWidth("Blue won!")) / 2, RES_Y / 2);
        }
        if (p2Lost && !p1Lost) {
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Red won!", (RES_X - metrics2.stringWidth("Red won!")) / 2, RES_Y / 2);
        }
        if (p1Lost && p2Lost) {
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("DRAW", (RES_X - metrics2.stringWidth("DRAW")) / 2, RES_Y / 2);
        }
        if(p1Lost || p2Lost) end();

    }

    public void end() {
        Timer timer2 = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //start();
            }
        });
        timer2.setRepeats(false); // Only execute once
        timer2.start(); // Go go go!
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            grid.moveBikes(direction1, direction2);
            p1Lost = grid.checkCollision(grid.getBike1(), grid.getBike2());
            p2Lost = grid.checkCollision(grid.getBike2(), grid.getBike1());
            isRunning = !p1Lost && !p2Lost;
        }
        if (!isRunning) timer.stop();
        repaint();
    }

    public class UpAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.DOWN)
                direction1 = Direction.UP;
        }
    }

    public class DownAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.UP)
                direction1 = Direction.DOWN;
        }
    }

    public class RightAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.LEFT) {
                direction1 = Direction.RIGHT;
            }
        }
    }

    public class LeftAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.RIGHT)
                direction1 = Direction.LEFT;
        }
    }

    public class UpAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.DOWN)
                direction2 = Direction.UP;
        }
    }

    public class DownAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.UP)
                direction2 = Direction.DOWN;
        }
    }

    public class RightAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.LEFT)
                direction2 = Direction.RIGHT;
        }
    }

    public class LeftAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.RIGHT)
                direction2 = Direction.LEFT;
        }
    }

}
