package messager.server.saveMessage;

public interface MessageRepository {
    boolean saveMessages(String messages);
    String loadMessages();
}
