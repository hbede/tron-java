import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements EndgameListener {
    boolean exit = false;
    GamePanel gamePanel;
    MenuPanel menuPanel;
    MultiPanel multiPanel;
    SettingsPanel settingsPanel;
    JPanel cards;
    CardLayout card;

    public GameFrame() {
        super();

        card = new CardLayout();

        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        multiPanel = new MultiPanel();
        settingsPanel = new SettingsPanel();
        gamePanel.addListener(this);

        cards = new JPanel(card);
        cards.add(gamePanel, "Game");
        cards.add(menuPanel, "Menu");
        cards.add(multiPanel, "Multiplayer");
        cards.add(settingsPanel, "Settings");

        var startButton = new JButton("Start local game");
        menuPanel.add(startButton);

        var multiButton = new JButton("Start multiplayer game");
        menuPanel.add(multiButton);

        var settingsButton = new JButton("Settings");
        menuPanel.add(settingsButton);

        var hostButton = new JButton("Host");
        multiPanel.add(hostButton);

        var joinButton = new JButton("Join");
        multiPanel.add(joinButton);

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
                card.show(cards, "Multiplayer");
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

        hostButton.addActionListener(e -> {
            try {
                card.show(cards, "Game");
                gamePanel.hostStart();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        joinButton.addActionListener(e -> {
            try {
                card.show(cards, "Game");
                gamePanel.clientStart();
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

    @Override
    public void someGameEnded() {
        card.show(cards, "Menu");
    }
}
