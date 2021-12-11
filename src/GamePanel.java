import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    private static int RES_X = 600;
    private static int RES_Y = 600;
    private static int UNIT_SIZE = 10;
    private static int DELAY = 400;
    static final int GAME_UNITS = (RES_X * RES_Y) / UNIT_SIZE;
    static boolean isRunning = false;
    private static Grid grid;
    private static Timer timer;
    private static Direction direction1 = Direction.RIGHT, direction2 = Direction.LEFT;
    private Controls controls = new Controls();
    boolean p1Lost = false;
    boolean p2Lost = false;
    private static boolean hostMode = false;
    private static boolean clientMode = false;
    static String serverMode;
    static NextPlayer nextPlayer;

    static boolean player1ready = false;
    static boolean player2ready = false;

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

    static Direction getDirection1() {
        return direction1;
    }

    static Direction getDirection2() {
        return direction2;
    }

    static void setDirection1(Direction d) {
        direction1 = d;
    }

    static void setDirection2(Direction d) {
        direction2 = d;
    }

    static boolean getHostMode() {
        return hostMode;
    }

    static boolean getClientMode() {
        return clientMode;
    }

    static void setHostMode(boolean h) {
        hostMode = h;
    }

    static void setClientMode(boolean c) {
        clientMode = c;
    }

    public void start() {
        hostMode = false;
        clientMode = false;
        isRunning = true;
        init();
        timer.start();
    }

    public void init() {
        grid = new Grid(RES_X, RES_Y, UNIT_SIZE, GAME_UNITS);
        timer = new Timer(DELAY, this);
        direction1 = Direction.RIGHT;
        direction2 = Direction.LEFT;
    }

    public void hostStart() {
        hostMode = true;
        clientMode = false;
        isRunning = true;
        init();
        timer.start();
    }

    public void clientStart() {
        clientMode = true;
        hostMode = false;
        isRunning = true;
        init();
        timer.start();
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
    }

    public void end() {
        Timer timer2 = new Timer(2000, arg0 -> {
            notifyEndgame();
            System.out.println("Stopped Running");
        });
        timer2.setRepeats(false); // Only execute once
        timer2.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            if(hostMode && !clientMode) {
                // direction2 = getDirFromClient();
            }
            if(!hostMode && clientMode) {
                // direction1 = getDirFromHost();
            }
            grid.moveBikes(direction1, direction2);
            p1Lost = grid.checkCollision(grid.getBike1(), grid.getBike2());
            p2Lost = grid.checkCollision(grid.getBike2(), grid.getBike1());
            isRunning = !p1Lost && !p2Lost;
            // System.out.println("Running");
        }
        if (!isRunning) {
            System.out.println("Not Running!");
            timer.stop();
            end();
        }
        repaint();
    }

    // controls
    public class UpAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.DOWN && !clientMode)
                direction1 = Direction.UP;
        }
    }

    public class DownAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.UP && !clientMode)
                direction1 = Direction.DOWN;
        }
    }

    public class RightAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.LEFT && !clientMode) {
                direction1 = Direction.RIGHT;
            }
        }
    }

    public class LeftAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.RIGHT && !clientMode)
                direction1 = Direction.LEFT;
                // sendDirToClient(direction1);
        }
    }

    public class UpAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.DOWN && !hostMode)
                direction2 = Direction.UP;
        }
    }

    public class DownAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.UP && !hostMode)
                direction2 = Direction.DOWN;
        }
    }

    public class RightAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.LEFT && !hostMode)
                direction2 = Direction.RIGHT;
        }
    }

    public class LeftAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.RIGHT && !hostMode)
                direction2 = Direction.LEFT;
        }
    }
    private List<EndgameListener> listeners = new ArrayList<>();

    public void addListener(EndgameListener toAdd) {
        listeners.add(toAdd);
    }

    public void notifyEndgame() {
        player1ready = false;
        player2ready = false;
        hostMode = false;
        clientMode = false;
        nextPlayer = NextPlayer.PNON;
        System.out.println("notifyEndgame() called");
        // Notify everybody that may be interested.
        for (EndgameListener hl : listeners)
            hl.someGameEnded();
    }

//    private List<PlayerReadyListener> listeners2 = new ArrayList<>();
//
//    public void addPlayerListener(PlayerReadyListener toAdd) {
//        listeners2.add(toAdd);
//    }
//
//    public void notifyPlayersReady() {
//        System.out.println("notifyPlayersReady() called");
//
//        // Notify everybody that may be interested.
//        for (PlayerReadyListener hl : listeners2)
//            hl.playersReady();
//    }

}
