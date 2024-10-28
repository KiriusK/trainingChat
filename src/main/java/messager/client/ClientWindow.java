package messager.client;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientWindow extends JFrame implements ClientView {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    JTextField ip, port, login, message, name;
    JPasswordField password;
    JTextArea viewMessage;
    JButton send, connect;
    JPanel topPanel;
    ClientService clientService;


    public ClientWindow(ClientService client) throws HeadlessException {
        clientService = client;
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Message Client");
        setLocationRelativeTo(null);
        send = new JButton("Send");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sendMessage(message.getText())) {
                    message.setText("");
                }
            }
        });
        connect = new JButton("Connect");
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });
        ip = new JTextField("127.0.0.1");
        port = new JTextField("1515");
        login = new JTextField("Login");
        password = new JPasswordField("pass");
        message = new JTextField();
        message.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (sendMessage(message.getText())) {
                        message.setText("");
                    }
                }
            }
        });
        name = new JTextField("User");
        topPanel = new JPanel(new GridLayout(2, 3));
        topPanel.add(ip);
        topPanel.add(port);
        topPanel.add(name);
        topPanel.add(login);
        topPanel.add(password);
        topPanel.add(connect);
        add(topPanel, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(message);
        bottomPanel.add(send, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        viewMessage = new JTextArea("console->\n");
        viewMessage.setEditable(false);
        JScrollPane jsp = new JScrollPane(viewMessage);
        add(jsp, BorderLayout.CENTER);
        setVisible(true);
    }



    @Override
    public void disconnectedServer() {
        topPanel.setVisible(true);
    }

    private boolean sendMessage(String text) {
        return clientService.sendMessage(text);
    }

    private void disconnect() {
        clientService.disconnectFromServer();
    }

    private void connect() {
        if (clientService.connectToServer(name.getText())) {
            topPanel.setVisible(false);
        }
    }

    @Override
    public void printMes(String mes) {
        viewMessage.append(mes);
        viewMessage.append("\n");
    }


    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            this.disconnect();
        }
    }
}