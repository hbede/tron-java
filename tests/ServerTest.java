import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServerTest {
    @Test
    public void testIPaddress(){
        ClientServer testClient = new ClientServer("192.168.0.16", 0, 0);
        Assertions.assertEquals("192.168.0.16", testClient.IPAddress);
    }
    @Test
    public void testLocalPort(){
        ClientServer testClient = new ClientServer("0", 44444, 0);
        Assertions.assertEquals(44444, testClient.localPortNumber);
    }
    @Test
    public void testPort(){
        ClientServer testClient = new ClientServer("0", 0, 44444);
        Assertions.assertEquals(44444, testClient.externalPortNumber);
    }
}
