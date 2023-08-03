import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class IRCClientGUI extends JFrame{
    private JTextArea chatArea;
    private JTextField inputField;
    public IRCClientGUI(IRCClient client) {
        super("Asher Chat");

        try {

            chatArea = new JTextArea(10, 30);
            chatArea.setEditable(false);
            JScrollPane chatScrollPane = new JScrollPane(chatArea);

            inputField = new JTextField(30);
            inputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    client.sendMessage(inputField.getText());
                    inputField.setText("");
                }
            });

            JButton sendButton = new JButton("Send");
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    client.sendMessage(inputField.getText());
                    inputField.setText("");
                }
            });

            JPanel inputPanel = new JPanel();
            inputPanel.add(inputField);
            inputPanel.add(sendButton);

            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(chatScrollPane, BorderLayout.CENTER);
            getContentPane().add(inputPanel, BorderLayout.SOUTH);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                IRCClient client=new IRCClient("irc.w3.org",6667,"asher","#testing");

                IRCClientGUI gui=new IRCClientGUI(client);
                client.chatArea=gui.chatArea;
            }
        });
    }
}