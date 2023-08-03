import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class IRCClient{
    private String SERVER_ADDRESS = "irc.example.com";
    private int SERVER_PORT = 6667;
    private String NICKNAME = "YourNickname";
    private String CHANNEL = "#yourchannel";

    public JTextArea chatArea;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public IRCClient(String SERVER_ADDRESS,int SERVER_PORT,String NICKNAME,String CHANNEL){
        this.SERVER_ADDRESS=SERVER_ADDRESS;
        this.SERVER_PORT=SERVER_PORT;
        this.NICKNAME=NICKNAME;
        this.CHANNEL=CHANNEL;
        try {
            socket = new Socket(this.SERVER_ADDRESS, this.SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            sendRawMessage("NICK " + this.NICKNAME);
            sendRawMessage("USER " + this.NICKNAME + " 0 * :Your Real Name");
            joinChannel(this.CHANNEL);
            startListening();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendRawMessage(String message) throws IOException {
        writer.write(message + "\r\n");
        writer.flush();
    }

    public  void joinChannel(String channel) throws IOException {
        sendRawMessage("JOIN " + channel);
    }

    public void sendMessage(String message) {
        try {
            sendRawMessage("PRIVMSG " + CHANNEL + " :" + message);
            chatArea.append("YOU : "+message+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startListening() {
        Thread listenerThread = new Thread(() -> {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Process incoming messages and display them
                    chatArea.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        listenerThread.start();
    }
}