package ru.zlygostev.chat;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static com.sun.javafx.fxml.expression.Expression.add;
import java.util.ArrayList;
import java.util.List;


public class MyChat extends JFrame {
    private JTextField chatField;
    private JTextArea chatHistory;
    private final String SERVER_ADDR = "localhost";
    private final String cmdAuthokString = "/authok";
    private final String cmdEndSessionString = "/end";
    private final String archiveFilename = "c:\\temp\\";
    private final String msgAuthokString = "Авторизация пройдена успешно";
    private final String msgErrSendString = "Ошибка отправки сообщения";
    private String nik;
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private Boolean authorized;
    private DataInputStream in;
    private DataOutputStream out;
    static List<String> msgsArchive = new ArrayList<String>();
    private final int maxSizeOfMsgsArchive = 100;


    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }
    public Boolean getAuthorized() { return this.authorized; }

    public MyChat() {
        setBounds(600, 300, 500, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chatHistory = new JTextArea();
        chatHistory.setEditable(false);
        chatHistory.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(chatHistory);
        add(jsp, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbSend = new JButton("SEND");
        bottomPanel.add(jbSend, BorderLayout.EAST);
        chatField = new JTextField();
        bottomPanel.add(chatField, BorderLayout.CENTER);
        chatField.grabFocus();
        jbSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!chatField.getText().trim().isEmpty()) {
                    sendMsg();
                    chatField.grabFocus();
                }
            }
        });
        chatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF("/end");
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException exc) {
                }
            }
        });
        setVisible(true);

        start();
    }

    public void start() {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.equals(cmdEndSessionString)) {
                            break;
                        }
                        if (str.startsWith(cmdAuthokString)) {
                            MyChat.this.setAuthorized(true);
                            String[] parts = str.split("\\s");
                            nik = parts[1];

                            // Считываем с диска историю сообщений для данного никнейма
                            final String filename = archiveFilename + "chat_" + nik + ".ser";
                            File f = new File(filename);
                            if (f.exists() && !f.isDirectory()) {
                                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                                    msgsArchive = (List<String>) in.readObject();
                                    in.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }



                                // Выводим архив сообщений в соответствующее поле
                                for(int i=0; i<msgsArchive.size(); i++)
                                    chatHistory.append(msgsArchive.get(i));
                            }

                            chatHistory.append("\n" + msgAuthokString + "\n");
                            break;
                        }
                        chatHistory.append(str + "\n");
                    }
                    if (MyChat.this.getAuthorized()) {
                        while (true) {
                            String str = in.readUTF() + "\n";
                            if (str.equals(cmdEndSessionString)) {
                                break;
                            }
                            chatHistory.append(str);

                            // Запись в архив сообщений
                            if (msgsArchive.size()>maxSizeOfMsgsArchive) {
                                msgsArchive.remove(0);
                            }
                            msgsArchive.add(str);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MyChat.this.setAuthorized(false);
                }
            }
        }).start();
    }

    public void sendMsg() {
        try {
            out.writeUTF(chatField.getText());
            if (chatField.getText().equals(cmdEndSessionString)) {
                // Для завершения связи с сервером отправляем команду "/end"

                if (!nik.isEmpty()) {
                    // Сохраняем в файл историю сообщений
                    try {
                        final String filename = archiveFilename + "chat_" + nik + ".ser";
//                        File f = new File(filename);
  //                      if (f.exists() && !f.isDirectory()) {
    //                        f.delete();
      //                  }

                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
                        out.writeObject(msgsArchive);
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.exit(0);
            }
            chatField.setText("");
        } catch (IOException e) {
            System.out.println(msgErrSendString);
        }
    }
}
