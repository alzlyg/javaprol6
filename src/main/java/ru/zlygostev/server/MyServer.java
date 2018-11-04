package ru.zlygostev.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyServer {
    private ServerSocket server;
    private Vector<ClientHandler> clients;
    private AuthService authService;
    private final int PORT = 8189;

    public boolean stopServer = false;

    private final String logFilename = "c:\\temp\\history.log";
    private boolean logsFileIsOpen = false;
    private List<String> logsArchive = new ArrayList<String>();


    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {
        try {
            server = new ServerSocket(PORT);
            Socket socket = null;
            authService = new BaseAuthService();
            authService.start();
            clients = new Vector<>();
            logsFileIsOpen = openLogFile();
            while (!stopServer) {
                writeToLog("Сервер ожидает подключения");
                socket = server.accept();
                writeToLog("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            writeToLog("Ошибка при работе сервера");
        } finally {
            try {
                server.close();
                closeLogFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.stop();
        }
    }

    private boolean openLogFile() {
        // Считываем с диска историю логов
        File f = new File(logFilename);
        if (f.exists() && !f.isDirectory()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(logFilename))) {
                logsArchive = (List<String>) in.readObject();
                in.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void closeLogFile() {
        // Сохраняем на диск логи
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(logFilename));
            out.writeObject(logsArchive);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized void writeToLog(String event) {
        // Сохраняем в файл историю логов
        logsArchive.add(event);
        System.out.println("LOG: " + event);
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) return true;
        }
        return false;
    }
    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
    public synchronized void sendMsgByNick(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nickTo)) {
                o.sendMsg(msg);
                return;
            }
        }
        from.sendMsg("Участник чата с ником " + nickTo + " не найден");
    }

    public synchronized void broadcastClientList() {
        StringBuilder sb = new StringBuilder("/clients ");
        for (ClientHandler o : clients) {
            sb.append(o.getName() + " ");
        }
        String msg = sb.toString();
        broadcastMsg(msg);
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }
    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
        broadcastClientList();
    }
}
