package messager.server.saveMessage;

public interface MessageSaveable {
    boolean saveMessages(String messages);
    String loadMessages();
}
