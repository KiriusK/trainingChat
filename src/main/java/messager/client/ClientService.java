package messager.client;


import messager.server.saveMessage.ServerService;

public class ClientService {
    private ServerService server;
    private ClientView view;
    private String clientName;
    private boolean isConnected;

    public ClientService(ServerService server) {
        this.server = server;
        setView();
    }

    private void setView() {
        view = new ClientWindow(this);
    }

    public void disconnected() {
        if (isConnected) {
            isConnected = false;
            view.disconnectedServer();
            printMes("Связь с севером потеряна!");
        }
    }


    public void disconnectFromServer() {
        server.disconnectClient(this);
    }

    public boolean connectToServer(String name) {
        clientName = name;
        isConnected = server.connectClient(this);
        if (isConnected) {
            printMes("Подключение удалось!");
            String log = server.getLog();
            if (log != null) {
                printMes(log);
            }
            return isConnected;
        } else {
            printMes("Подключение не удалось!");
            return false;
        }
    }

    public boolean sendMessage(String text) {
        if (isConnected) {
            if (!text.isEmpty()) {
                return server.message(clientName + ": " + text);
            }
        } else {
            printMes("Нет подключения к серверу");

        }
        return false;
    }

    public void messageFromServer(String mes) {
        printMes(mes);
    }

    private void printMes(String mes) {
        view.printMes(mes);
    }


}
