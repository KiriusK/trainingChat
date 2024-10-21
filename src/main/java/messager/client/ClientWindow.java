package messager.client;

import messager.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClientWindow extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    ServerWindow server;
    JTextField ip, port, login, message, name;
    JPasswordField password;
    JTextArea viewMessage;
    JButton send;
    boolean isConnected;

    public ClientWindow(ServerWindow server, String userName) throws HeadlessException {
        this.server = server;
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
        name = new JTextField(userName);
        JPanel topPanel = new JPanel(new GridLayout(2, 3));
        topPanel.add(ip);
        topPanel.add(port);
        topPanel.add(name);
        topPanel.add(login);
        topPanel.add(password);
        add(topPanel, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(message);
        bottomPanel.add(send);
        add(bottomPanel, BorderLayout.SOUTH);
        viewMessage = new JTextArea("console->\n");
        viewMessage.setEditable(false);
        JScrollPane jsp = new JScrollPane(viewMessage);
        add(jsp, BorderLayout.CENTER);
        setVisible(true);
        connectServer();
    }

    private JPanel createBottomJPanel() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints constrain = new GridBagConstraints();
        constrain.fill = GridBagConstraints.HORIZONTAL;
        constrain.weightx = 4.0;
        constrain.gridwidth = 4;
        JPanel jp = new JPanel(grid);
        constrain.gridwidth = GridBagConstraints.RELATIVE;
        grid.setConstraints(message, constrain);
        jp.add(message);
        constrain.gridwidth = GridBagConstraints.REMAINDER;
        grid.setConstraints(send, constrain);
        jp.add(send);
        return jp;
    }


    private void connectServer() {
        isConnected = server.isServerWork();
        if (isConnected) {
            printMes("Соединение установлено");
            printMes(server.getLog());
        } else {
            printMes("Сервер недоступен");
        }
    }

    private void printMes(String mes) {
        viewMessage.append(mes);
        viewMessage.append("\n");
    }

    private boolean sendMessage(String message) {
        if (isConnected) {
            server.createLogRec(name.getText(), message);
            viewMessage.setText(server.getLog());
            return true;
        } else {
            printMes("Соединение не установлено, пытаюсь установить. Попробуйте еще раз.");
            connectServer();
            return false;
        }
    }
}
