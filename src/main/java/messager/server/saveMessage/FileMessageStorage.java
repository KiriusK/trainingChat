package messager.server.saveMessage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileMessageStorage implements MessageRepository {
    private final String fileName;

    public FileMessageStorage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean saveMessages(String messages) {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(messages);
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения в файл:\n" + e.getMessage());
        }
        return false;
    }

    @Override
    public String loadMessages() {
        StringBuilder stBuild = new StringBuilder();
        try (FileReader fr = new FileReader(fileName); Scanner scan = new Scanner(fr)) {
            while (scan.hasNext()) {
                stBuild.append(scan.nextLine());
                stBuild.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла:\n" + e.getMessage());
        }
        return stBuild.toString();
    }
}
