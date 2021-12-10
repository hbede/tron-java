import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    boolean exit = false;

    public GameFrame() {
        super();
        CardLayout card = new CardLayout();

        var menuPanel = new MenuPanel();
        var gamePanel = new GamePanel();

        JPanel cards = new JPanel(card);
        cards.add(gamePanel, "Game");
        cards.add(menuPanel, "Menu");

        JButton startButton = new JButton("Start local game");
        menuPanel.add(startButton);

        JButton multiButton = new JButton("Start multiplayer game");
        menuPanel.add(multiButton);

        JButton settingsButton = new JButton("Settings");
        menuPanel.add(settingsButton);

        add(cards);
        card.show(cards, "Menu");

        startButton.addActionListener(e -> {
            try {
                card.show(cards, "Game");
                gamePanel.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        multiButton.addActionListener(e -> {
            try {
                card.show(cards, "Game");
                gamePanel.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        settingsButton.addActionListener(e -> {
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
