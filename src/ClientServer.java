import java.io.IOException;
import java.net.*;

/**
 * A UDP server threaded class to handle communication between two game clients.
 * It always runs on both player's machine and sends and receives messages.
 */
public class ClientServer extends Thread{

    String IPAddress;
    int externalPortNumber;
    int localPortNumber;

    boolean isWaiting = false;
    boolean isReady = false;
    boolean isp1Ready = false;

    /**
     * Constructor for
     * @param address the IP address of the other player.
     * @param localpNum the local port.
     * @param pNum the port of the other player.
     */
    public ClientServer(String address, int localpNum, int pNum) {
        IPAddress = address;
        externalPortNumber = pNum;
        localPortNumber = localpNum;
    }

    /**
     *  This function runs when the thread has started.
     *  It sends data to the other player and listens
     *  if there is incoming data in an endless cycle.
     *  The server sends strings converted to bytes.
     *  If the player is host (or p1), the server will
     *  send the directions it gets from the getDirection1() method.
     *  If the player is client (or p2), the server will
     *  send the directions it gets from the getDirection2() method.
     *  If the player is p1, and they are ready, the server sends
     *  the word WAITING, to notify the other player, that they are ready.
     *  The server of the other server notes this.
     *  If the player is p2, and they are ready, the server sends
     *  the word READY, to notify the other player, that they are ready.
     *  The server of the other server notes this.
     *  If both players are ready, the game starts
     *  and all flags that are used to get to know
     *  if the other player is ready are set back to default,
     *  so the players will be able to play again.
     *  The directions that the server gets as answers are used to set
     *  the direction of the opponent.
     */
    public void run() {

        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(localPortNumber);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress ia = null;
        try {
            ia = InetAddress.getByName(IPAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        while(true) {
            String out = "OK";

            if(GamePanel.getHostMode())  {
                out = GamePanel.getDirection1().toString();
            }
            if(GamePanel.getClientMode()) {
                out = GamePanel.getDirection2().toString();
            }

            if(GamePanel.player1ready && !GamePanel.player2ready) {
                System.out.println("P1READY AND P2 NOT! READY");
                isWaiting = true;
            }
            if (GamePanel.player2ready && !GamePanel.player1ready) {
                System.out.println("P2READY AND P1 NOT! READY");
                isReady = true;
            }

            if(isWaiting) {
                out = "WAITING";
            }

            if(isReady) {
                out = "READY";
            }

            byte[] buffer = out.getBytes();

            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, ia, externalPortNumber);

            try {
                if (datagramSocket != null) {
                    datagramSocket.send(datagramPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
////////////////////////////////////////////////////////////////////////
            //receiving data from server
            buffer = new byte[1024];

            DatagramPacket response = new DatagramPacket(buffer, buffer.length);

            try {
                datagramSocket.receive(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String answer = new String(buffer, 0, response.getLength());
            System.out.println("Other player sent: - " + answer + " isWaiting: " + isWaiting +  ", isReady: " + isReady);

            if (GamePanel.getHostMode() && !answer.equals("WAITING") && !answer.equals("READY") && !answer.equals("OK")) {
                GamePanel.setDirection2(Direction.valueOf(answer));
            }
            if (GamePanel.getClientMode()  && !answer.equals("WAITING") && !answer.equals("READY") && !answer.equals("OK")){
                GamePanel.setDirection1(Direction.valueOf(answer));
            }

            if(answer.equals("WAITING")) isp1Ready = true;
            if (isReady && isp1Ready) {
                System.out.println("ISREADY AND ANSWER IS WAITING");
                GameFrame.card.show(GameFrame.cards, "Game");
                isReady = false;
                isWaiting = false;
                isp1Ready = false;
                GamePanel.player2ready = false;
                GamePanel.player1ready = false;
            }

            if (isWaiting && answer.equals("READY")) {
                System.out.println("ISWAITING AND ANSWER IS READY");
                GameFrame.card.show(GameFrame.cards, "Game");
                isWaiting = false;
                isReady = false;
                GamePanel.player2ready = false;
                GamePanel.player1ready = false;
            }
        }
    }
}