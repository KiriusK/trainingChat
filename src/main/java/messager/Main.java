package messager;

import messager.client.ClientService;
import messager.client.ClientWindow;
import messager.server.ServerWindow;
import messager.server.saveMessage.ServerService;

public class Main {
    public static void main(String[] args) {
        ServerService server = new ServerService();
        ClientService client0 = new ClientService(server);
        ClientService client1 = new ClientService(server);
    }
}
