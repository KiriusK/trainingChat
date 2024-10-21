package messager.server;

import messager.server.saveMessage.SaveMesToFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.FileNameMap;

public class ServerWindow extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private boolean isServerWorking;
    private final JButton start, stop;
    private final JTextArea textOut;
    private StringBuilder log;
    private SaveMesToFile fileMessages;


    public ServerWindow() throws HeadlessException {
        isServerWorking = false;
        log = new StringBuilder();
        fileMessages = new SaveMesToFile("server.log");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Message Server");
        setLocationRelativeTo(null);

        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
        stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });
    JPanel jp = new JPanel(new GridLayout(1, 2));
    jp.add(start);
    jp.add(stop);
    textOut = new JTextArea("console->\n");
    textOut.setEditable(false);
    JScrollPane jsp = new JScrollPane(textOut);
    add(jsp, BorderLayout.CENTER);
    add(jp, BorderLayout.SOUTH);
    setVisible(true);
    }

    private void stopServer() {
        if (!isServerWorking) {
            printMes("Server already stopped!");
            return;
        }
        isServerWorking = false;
        try {
            printMes("Server is stopping!");
            fileMessages.saveMessages(log.toString());
        } catch (RuntimeException ex) {
            printMes(ex.getMessage());
        }
    }

    private void startServer() {
        if (isServerWorking) {
            printMes("Server already runned!");
            return;
        }
        isServerWorking = true;
        try {
            printMes("Server is running!");
            log = new StringBuilder(fileMessages.loadMessages());
        } catch (RuntimeException ex) {
            printMes(ex.getMessage());
        }
    }

    private void printMes(String mes) {
        textOut.append(mes);
        textOut.append("\n");
    }

    public boolean isServerWork() {
        return isServerWorking;
    }

    public boolean createLogRec(String name, String mes) {
        log.append(name);
        log.append(": ");
        log.append(mes);
        log.append("\n");
        return true;
    }

    public String getLog() {
        return log.toString();
    }
}
