import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements EndgameListener {
    boolean dataRcv = false;
    boolean exit = false;
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

    public GameFrame() {
        super();


        card = new CardLayout();

        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        multiPanel = new MultiPanel();
        chooseNumberPanel = new JPanel();
        settingsPanel = new SettingsPanel();
        gamePanel.addListener(this);
        // gamePanel.addPlayerListener(this);

        cards = new JPanel(card);
        cards.add(gamePanel, "Game");
        cards.add(menuPanel, "Menu");
        cards.add(multiPanel, "Multiplayer");
        cards.add(chooseNumberPanel, "Numbers");
        cards.add(settingsPanel, "Settings");

        var startButton = new JButton("Start local game");
        menuPanel.add(startButton);

        var multiButton = new JButton("Start multiplayer game");
        menuPanel.add(multiButton);

        var settingsButton = new JButton("Settings");
        menuPanel.add(settingsButton);

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
                GamePanel.nextPlayer = NextPlayer.PNON;
                card.show(cards, "Numbers");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        hostButton.addActionListener(e -> {
            try {
                GamePanel.player1ready = true;
                GamePanel.nextPlayer = NextPlayer.URP2;
                gamePanel.hostStart();
                //card.show(cards, "Game");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        joinButton.addActionListener(e -> {
            try {
                GamePanel.player2ready = true;
                GamePanel.nextPlayer = NextPlayer.URP1;
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

    @Override
    public void someGameEnded() {
        card.show(cards, "Menu");
    }

//    @Override
//    public void playersReady() {
//        if (GamePanel.getHostMode()) {
//            gamePanel.clientStart();
//        } else if (GamePanel.getClientMode()) {
//            gamePanel.hostStart();
//        } else gamePanel.start();
//        card.show(cards, "Game");
//    }
}
