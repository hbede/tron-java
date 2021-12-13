import javax.swing.*;
import java.awt.*;

/**
 * This JFrame class contains all GUI elements.
 * It uses a card layout to handle the Menu System.
 */
public class GameFrame extends JFrame implements EndgameListener, OtherPlayerReadyListener {
    boolean dataRcv = false;
    GamePanel gamePanel;
    MenuPanel menuPanel;
    MultiPanel multiPanel;
    JPanel chooseNumberPanel;
    SettingsPanel settingsPanel;
    static JPanel cards;
    static CardLayout card;

    String IP;
    int localPort;
    int clientPort;

    /**
     * Constructor of class GameFrame.
     * It adds Swing elements to the GUI.
     * Also adds listeners to the buttons and text boxes.
     */
    public GameFrame() {
        super();


        card = new CardLayout();

        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        multiPanel = new MultiPanel();
        chooseNumberPanel = new JPanel();
        settingsPanel = new SettingsPanel();
        gamePanel.addListener(this);

        cards = new JPanel(card);
        cards.add(gamePanel, "Game");
        cards.add(menuPanel, "Menu");
        cards.add(multiPanel, "Multiplayer");
        cards.add(chooseNumberPanel, "Numbers");
        cards.add(settingsPanel, "Settings");

        var startButton = new JButton("Start local game");
        startButton.setPreferredSize(new Dimension(170, 100));
        menuPanel.add(startButton);

        var multiButton = new JButton("Start multiplayer game");
        multiButton.setPreferredSize(new Dimension(170, 100));
        menuPanel.add(multiButton);

        var settingsButton = new JButton("Settings");
        //menuPanel.add(settingsButton);

        var hostButton = new JButton("Player 1");
        chooseNumberPanel.add(hostButton);

        var joinButton = new JButton("Player 2");
        chooseNumberPanel.add(joinButton);

        var panel1 = new JPanel();
        var panel2 = new JPanel();
        var panel3 = new JPanel();

        var joinIPText = new JTextField("192.168.1.213", 30);
        panel1.add(joinIPText);
        multiPanel.add(panel1);

        var joinPortText = new JTextField(8);
        panel2.add(joinPortText);

        var hostPortText = new JTextField(8);
        panel2.add(hostPortText);
        multiPanel.add(panel2);

        var multiOKButton = new JButton("OK");
        panel3.add(multiOKButton);
        multiPanel.add(panel3);

        multiPanel.setLayout(new FlowLayout());


        add(cards);
        card.show(cards, "Menu");

        startButton.addActionListener(e -> {
            try {
                card.show(cards, "Game");
                System.out.println("Game Panel shown by Start Button");
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
                System.out.println("Game Panel shown by Settings Button");
                gamePanel.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        multiOKButton.addActionListener(e -> {
            try {
                IP = joinIPText.getText();
                clientPort = Integer.parseInt(joinPortText.getText());
                localPort = Integer.parseInt(hostPortText.getText());
                dataRcv = true;
                card.show(cards, "Numbers");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        hostButton.addActionListener(e -> {
            try {
                GamePanel.player1ready = true;
                //gamePanel.hostStart();
                //card.show(cards, "Game");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        joinButton.addActionListener(e -> {
            try {
                GamePanel.player2ready = true;
                gamePanel.clientStart();
                //card.show(cards, "Game");
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

    /**
     * Overridden function of EndgameListener.
     * Shows the Menu Panel using CardLayout.
     */
    @Override
    public void someGameEnded() {
        card.show(cards, "Menu");
    }

    @Override
    public void otherPlayerReady() {
        gamePanel.hostStart();
    }
}