/**
 * This class initializes a GameFrame Object in a Thread.
 */
public class TronGame extends Thread {
    GameFrame gg;

    /**
     * Runs GameFrame in a Thread.
     */
    public void run() {
        gg = new GameFrame();
    }
}