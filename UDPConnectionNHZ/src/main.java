import java.io.IOException;

public class main {
    public static void main(String args[]) throws IOException {
        ClientServer client = new ClientServer("192.168.0.16", 44444, 55555);
        client.run();
    }
}
