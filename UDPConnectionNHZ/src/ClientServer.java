import java.io.IOException;
import java.net.*;
import static java.lang.Integer.parseInt;

public class ClientServer extends Thread {

    String IPaddr;
    int portNum;
    int localPortNum;

    public ClientServer(String address, int localpNum, int pNum) {
        IPaddr = address;
        portNum = pNum;
        localPortNum = localpNum;
    }

    public void run() {


       // ClientFrame frame = new ClientFrame();
       // frame.createFrame();
//
       // while (frame.getClosed() == false) {
       //     System.out.println("frame is opened");
       // }

       // String[] ertekek = frame.getValues();
       // IPaddr = ertekek[0];
       // portNum = parseInt(ertekek[1]);
       // localPortNum = parseInt(ertekek[2]);
       // System.out.println(IPaddr + "\n" + portNum);
        //////////////////////////////
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
        for (int i = 95; i <= 121; i++) {
            //sending message from stdin
            //lehet a string foloslegesnek tunik, de nem az, anelkul random nem kuldott el uzeneteket meg ekezetes uzeneteket
            //String input = scanner.nextLine();
            //byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
            byte byteArray[] = {(byte) i};
            DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length, ia, portNum);

            try {
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //receiving data from server
            byte[] buffer = new byte[1024];

            DatagramPacket response = new DatagramPacket(buffer, buffer.length);

            try {
                datagramSocket.receive(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String answer = new String(buffer, 0, response.getLength());
            //System.out.println(parseInt(answer));
            System.out.println("Server sent: -" + answer);

            //ha a whileban van ez a kriterium, az utolso "." uzenetet
            // mar nem kuldi el es nem all le a szerver
            //if (answer.equals(".")) break;
        }
    }
}
