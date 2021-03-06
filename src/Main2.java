/**
 * A main class to start the second program.
 * It works exactly like the class Main.
 */
public class Main2 {
    public static void main(String[] args) {
        TronGame tg = new TronGame();
        tg.run();

        System.out.println("After GameFrame");
        while (!tg.gg.dataRcv) {
            System.out.println("Data not received");
        }

        System.out.println("Server Start");
        ClientServer client = new ClientServer(tg.gg.IP, tg.gg.localPort, tg.gg.clientPort);
        client.run();
    }
}
