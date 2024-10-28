package messager.server;

import messager.server.saveMessage.FileMessageStorage;
import messager.server.saveMessage.ServerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ServerWindow extends JFrame implements ServerView{
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private final JButton start, stop;
    private final JTextArea textOut;
    private ServerService service;



    public ServerWindow(ServerService service) throws HeadlessException {
        this.service = service;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Message Server");
        setLocationRelativeTo(null);

        start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                service.startServer();
            }
        });
        stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                service.stopServer();
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



    public void printMes(String mes) {
        textOut.append(mes);
        textOut.append("\n");
    }

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            service.disconnectAllClients();
        }
    }

}
