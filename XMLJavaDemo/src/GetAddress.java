import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetAddress {
    public static void main(String[] args) {
        try {
            InetAddress[] inetAddress = InetAddress.getAllByName("gpcoder.com");
            System.out.println(inetAddress[0]);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
