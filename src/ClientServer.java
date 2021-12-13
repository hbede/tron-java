import java.io.IOException;
import java.net.*;

/**
 * A UDP server threaded class to handle communication between two game clients.
 * It always runs on both player's machine and sends and receives messages.
 */
public class ClientServer extends Thread {

    String IPAddress;
    int externalPortNumber;
    int localPortNumber;

    boolean isWaiting = false;
    boolean isReady = false;
    boolean isp1Ready = false;

    /**
     * Constructor for
     * @param address
     * @param localpNum
     * @param pNum
     */
    public ClientServer(String address, int localpNum, int pNum) {
        IPAddress = address;
        externalPortNumber = pNum;
        localPortNumber = localpNum;
    }

    /**
     *  This function runs when the thread has started.
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