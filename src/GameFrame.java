import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    public GameFrame() {
        super();
        CardLayout card = new CardLayout();
        var menuPanel = new MenuPanel();
        var gamePanel = new GamePanel();
        JPanel cards = new JPanel(card);
        cards.add(gamePanel, "Game");
        cards.add(menuPanel, "Menu");
        JButton startButton = new JButton("Start");
        startButton.setBounds(50, 50, 70, 30);
        menuPanel.add(startButton);
        card.show(cards, "Menu");
        add(cards);
        startButton.addActionListener(e -> {
            try {
                card.show(cards, "Game");
                gamePanel.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        setTitle("TRON-java");
        setResizable(false);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
