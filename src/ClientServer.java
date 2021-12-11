import java.io.IOException;
import java.net.*;
import static java.lang.Integer.parseInt;

public class ClientServer extends Thread {

    String IPaddr;
    int portNum;
    int localPortNum;
    boolean displayed = false;
    boolean URP1sent = false;
    boolean URP2sent = false;

    public ClientServer(String address, int localpNum, int pNum) {
        IPaddr = address;
        portNum = pNum;
        localPortNum = localpNum;
    }

    public void run() {

        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(localPortNum);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress ia = null;
        try {
            ia = InetAddress.getByName(IPaddr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //while (true) {
        while(true) {
            if(!GamePanel.player2ready && !GamePanel.player1ready) displayed = false;
            //System.out.println("ciklus eleje");
            String outDir = "NOTHING";
            //System.out.println("nothing utan");
            String inDir;


//            if (!modeSent) {
//                outDir = GamePanel.serverMode;
//                modeSent = true;
//            }


            if(GamePanel.getHostMode())  {
                outDir = GamePanel.getDirection1().toString();
                //System.out.println("Direction 1 (will be sent): " + outDir);
            }
            if(GamePanel.getClientMode()) {
                outDir = GamePanel.getDirection2().toString();
                //System.out.println("Direction 2 (will be sent): " + outDir);
            }

            if (GamePanel.nextPlayer.equals(NextPlayer.URP2) && !URP2sent) {
                outDir = "URP2";
                //URP2sent = true;

            }

            if (GamePanel.nextPlayer.equals(NextPlayer.URP1) && !URP1sent) {
                outDir = "URP1";
                //URP1sent = true;
            }

            byte[] buffer = outDir.getBytes();

            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, ia, portNum);

            try {
                datagramSocket.send(datagramPacket);
                //System.out.println(outDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("nothing utan");
////////////////////////////////////////////////////////////////////////
            //receiving data from server
            buffer = new byte[1024];

            DatagramPacket response = new DatagramPacket(buffer, buffer.length);

            try {
                //System.out.println("before Server response");
                datagramSocket.receive(response);
                //System.out.println("after Server response");
            } catch (IOException e) {
                e.printStackTrace();
            }
            String answer = new String(buffer, 0, response.getLength());
            if(answer.equals("URP1")){
                System.out.println("Server sent: - " + answer);
            }

            if (GamePanel.getHostMode() && !answer.equals("URP2") && !answer.equals("URP1") && !answer.equals("NOTHING")) {
                GamePanel.setDirection2(Direction.valueOf(answer));
            }
            if (GamePanel.getClientMode() && !answer.equals("URP2") && !answer.equals("URP1") && !answer.equals("NOTHING")){
                GamePanel.setDirection1(Direction.valueOf(answer));
            }
            if (answer.equals("URP2")) {
                // player 2 mode activated
//                GamePanel.setClientMode(true);
//                GamePanel.setHostMode(false);
//                GameFrame.card.show(GameFrame.cards, "Game");
                GamePanel.player1ready = true;
            }
            if (answer.equals("URP1")) {
                // player 1 mode activated
//                GamePanel.setHostMode(true);
//                GamePanel.setClientMode(false);
//                GameFrame.card.show(GameFrame.cards, "Game");
                GamePanel.player2ready = true;
            }

            if (!displayed && GamePanel.player1ready && GamePanel.player2ready) {
                System.out.println("P1 ReadyEND? " + GamePanel.player1ready);
                System.out.println("P2 ReadyEND? " + GamePanel.player2ready);
                GameFrame.card.show(GameFrame.cards, "Game");
                //System.out.println("Game Panel shown by Server");
                //GamePanel.hostStart();
                displayed = true;
                URP2sent = false;
                URP1sent = false;
                GamePanel.nextPlayer = NextPlayer.PNON;

            }

            //ha a whileban van ez a kriterium, az utolso "." uzenetet
            // mar nem kuldi el es nem all le a szerver
            //if (answer.equals(".")) break;
            //System.out.println("ciklus vége");
        }

    }
}