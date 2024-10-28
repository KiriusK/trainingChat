package messager.server;

import messager.client.ClientService;
import messager.server.saveMessage.FileMessageStorage;

import java.util.ArrayList;
import java.util.List;

public class ServerService {
    private List<ClientService> clientsList;
    private boolean isServerWorking;
    private StringBuilder log;
    private FileMessageStorage fileMessages;
    private ServerView view;

    public ServerService() {
        clientsList = new ArrayList<>();
        log = new StringBuilder();
        fileMessages = new FileMessageStorage("server.log");
        setView();
    }

    private void setView() {
        view = new ServerWindow(this);
    }


    public void stopServer() {
        if (!isServerWorking) {
            view.printMes("Server already stopped!");
            return;
        }
        isServerWorking = false;
        try {
            disconnectAllClients();
            view.printMes("Server is stopping!");
            fileMessages.saveMessages(log.toString());
        } catch (RuntimeException ex) {
            view.printMes(ex.getMessage());
        }
    }

    public void startServer() {
        if (isServerWorking) {
            view.printMes("Server already runned!");
            return;
        }
        isServerWorking = true;
        try {
            view.printMes("Server is running!");
            log = new StringBuilder(fileMessages.loadMessages());
        } catch (RuntimeException ex) {
            view.printMes(ex.getMessage());
        }
    }


    public boolean createLogRec(String mes) {
        log.append(mes);
        log.append("\n");
        return true;
    }

    public String getLog() {
        return log.toString();
    }

    public boolean connectClient(ClientService client) {
        try {
            if (isServerWorking) {
                clientsList.add(client);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            view.printMes("Ошибка: " + ex.getMessage());
            return false;
        }
    }

    public boolean disconnectClient(ClientService client) {
        try {
            if (!isServerWorking) {
                clientsList.removeIf(cl -> cl.equals(client));
                return true;
            } else {
                return false;
            }
        } catch(Exception ex) {
            view.printMes("Ошибка: " + ex.getMessage());
            return false;
        }
    }

    public void disconnectAllClients() {
        for (ClientService client: clientsList) {
            client.disconnected();
        }
    }

    public boolean message(String mes) {
        try {
            createLogRec(mes);
            fileMessages.saveMessages(log.toString());
            view.printMes(mes);
            for (ClientService client : clientsList) {
                client.messageFromServer(mes);
            }
            return true;
        } catch (Exception ex) {
            view.printMes("Ошибка: " + ex.getMessage());
            return false;
        }
    }
}
