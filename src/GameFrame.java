import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        add(new GamePanel());
        setTitle("TRON-java");
        setResizable(false);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
