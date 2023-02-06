package Client;

public class Client {
    public static void main(String[] args) {
        ServerCon serverCon = new ServerCon();
        ClientView clientView = ClientView.getInstance();
        clientView.serverCon = serverCon;
    }
}
