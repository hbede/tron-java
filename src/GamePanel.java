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

/**
 * A JPanel to handle the game.
 */
public class GamePanel extends JPanel implements ActionListener {
    private static final int RES_X = 600;
    private static final int RES_Y = 600;
    private static final int UNIT_SIZE = 10;
    private static final int DELAY = 200;
    static final int GAME_UNITS = (RES_X * RES_Y) / (UNIT_SIZE * UNIT_SIZE);
    static boolean isRunning = false;
    private static Grid grid;
    private static Timer timer;
    private static Direction direction1 = Direction.RIGHT, direction2 = Direction.LEFT;
    boolean p1Lost = false;
    boolean p2Lost = false;
    private static boolean hostMode = false;
    private static boolean clientMode = false;

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

    /**
     * Constructor for the game panel.
     * It contains mostly control binding setups.
     */
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

    /**
     * Returns direction.
     * Used to tell the first bike where to move.
     * @return direction
     */
    static Direction getDirection1() {
        return direction1;
    }

    /**
     * Returns direction.
     * Used to tell the second bike where to move.
     * @return direction
     */
    static Direction getDirection2() {
        return direction2;
    }

    /**
     * Sets direction.
     * Used to tell the first bike where to move.
     * @param d direction, where te bike should move
     */
    static void setDirection1(Direction d) {
        direction1 = d;
    }

    /**
     * Sets direction.
     * Used to tell the second bike where to move.
     * @param d direction, where te bike should move
     */
    static void setDirection2(Direction d) {
        direction2 = d;
    }

    /**
     * Returns actual hostMode setting of GamePanel.
     * @return true, if the game is in host mode
     */
    static boolean getHostMode() {
        return hostMode;
    }

    /**
     * Returns actual clientMode setting of GamePanel.
     * @return true, if the game is in client mode
     */
    static boolean getClientMode() {
        return clientMode;
    }

    /**
     * Called when the game is started in local mode.
     */
    public void start() {
        hostMode = false;
        clientMode = false;
        isRunning = true;
        init();
        timer.start();
    }

    /**
     * Used to initialize a few variables when the game starts.
     */
    public void init() {
        grid = new Grid(RES_X, RES_Y, UNIT_SIZE, GAME_UNITS);
        timer = new Timer(DELAY, this);
        direction1 = Direction.RIGHT;
        direction2 = Direction.LEFT;
    }

    /**
     * Called when the game is started in host mode.
     */
    public void hostStart() {
        hostMode = true;
        clientMode = false;
        isRunning = true;
        init();
        timer.start();
    }

    /**
     * Called when the game is started in client mode.
     */
    public void clientStart() {
        clientMode = true;
        hostMode = false;
        isRunning = true;
        init();
        timer.start();
    }

    /**
     * Used to draw AWT Graphics.
     * @param g a Graphics object to draw with
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Used to draw the grid and the endgame texts.
     * @param g a Graphics object to draw with
     */
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

    /**
     * Called when the game has ended.
     * It uses a timer to delay jumping back to the menu.
     */
    public void end() {
        Timer timer2 = new Timer(3000, arg0 -> {
            notifyEndgame();
            System.out.println("Stopped Running");
        });
        timer2.setRepeats(false); // Only execute once
        timer2.start();
    }

    /**
     * Overridden function of ActionListener, it is performed when the timer ticks.
     * If the game is running, moves the bikes and checks collision.
     * If the game is not running, stops the timer and calls the end() function.
     * Repaints the GUI in every tick.
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            grid.moveBikes(direction1, direction2);
            p1Lost = grid.checkCollision(grid.getBike1(), grid.getBike2());
            p2Lost = grid.checkCollision(grid.getBike2(), grid.getBike1());
            isRunning = !p1Lost && !p2Lost;
        }
        if (!isRunning) {
            timer.stop();
            end();
        }
        repaint();
    }

    /**
     * Inner class to handle up action of player 1.
     */
    public static class UpAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.DOWN && !clientMode)
                direction1 = Direction.UP;
        }
    }

    /**
     * Inner class to handle down action of player 1.
     */
    public static class DownAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.UP && !clientMode)
                direction1 = Direction.DOWN;
        }
    }

    /**
     * Inner class to handle right action of player 1.
     */
    public static class RightAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.LEFT && !clientMode) {
                direction1 = Direction.RIGHT;
            }
        }
    }

    /**
     * Inner class to handle left action of player 1.
     */
    public static class LeftAction1 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction1 != Direction.RIGHT && !clientMode)
                direction1 = Direction.LEFT;
        }
    }

    /**
     * Inner class to handle up action of player 2.
     */
    public static class UpAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.DOWN && !hostMode)
                direction2 = Direction.UP;
        }
    }

    /**
     * Inner class to handle down action of player 2.
     */
    public static class DownAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.UP && !hostMode)
                direction2 = Direction.DOWN;
        }
    }

    /**
     * Inner class to handle right action of player 2.
     */
    public static class RightAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.LEFT && !hostMode)
                direction2 = Direction.RIGHT;
        }
    }

    /**
     * Inner class to handle left action of player 2.
     */
    public static class LeftAction2 extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (direction2 != Direction.RIGHT && !hostMode)
                direction2 = Direction.LEFT;
        }
    }

    private final List<EndgameListener> listeners = new ArrayList<>();

    public void addListener(EndgameListener toAdd) {
        listeners.add(toAdd);
    }

    /**
     * Executed when a "game ended" event happens.
     */
    public void notifyEndgame() {
        player1ready = false;
        player2ready = false;
        hostMode = false;
        clientMode = false;
        System.out.println("notifyEndgame() called");
        // Notify everybody that may be interested
        for (EndgameListener hl : listeners)
            hl.someGameEnded();
    }
}
