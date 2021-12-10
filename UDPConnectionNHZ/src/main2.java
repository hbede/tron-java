import java.io.IOException;

public class main2 {
    public static void main(String[] args) throws IOException {
        ClientServer client = new ClientServer("192.168.0.16", 55555, 44444);
        client.run();
    }
}
