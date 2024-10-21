package messager;

import messager.client.ClientWindow;
import messager.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow server = new ServerWindow();
        ClientWindow client0 = new ClientWindow(server, "Саня");
        ClientWindow client1 = new ClientWindow(server, "Вася");
    }
}
