/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import Models.Client;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author aaron
 */
public class ClientSocket {

    private Client c;

    private Socket socket;
    private long timeOutMilis = 5000;

    private BufferedReader in;
    private PrintWriter out;

    private boolean tryToReconnect = true;
    private boolean isExit = false;
    private Thread mainThread;

    public ClientSocket(Client c) throws IOException {
        this.c = c;
    }

    public Socket getSocket() {
        return socket;
    }

    public Client getClient() {
        return c;
    }

    public void setClient(Client c) {
        this.c = c;
    }

    public void connect() throws IOException {

        while (tryToReconnect) {
            System.out.println("Trying to connect...");
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(c.getHost(), c.getPort()));

                if (socket.isConnected()) {
                    tryToReconnect = false;
                }

                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                JSONObject obj = new JSONObject();

                obj.put("name", c.getName());
                obj.put("message", "connected");
                obj.put("from", c.getHost().toString());
                obj.put("to", "SERVER");                //To the chat group??

                out.println(obj.toString());

                if (in != null) {
                    System.out.println("[received] " + in.readLine() + "\n");
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void listen() throws IOException {
        mainThread = new Thread(() -> {
            String response;
            try {
                while (true) {
                    response = in.readLine();
                    System.out.println("[received]: " + response);
                }
            } catch (IOException ex) {
                System.out.println("Socket disconected");
                try {
                    if (!isExit) {
                        tryToReconnect = true;
                        this.connect();
                    }
                } catch (IOException ex1) {
                    Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        });
        mainThread.start();

    }

    public <T> void send(T data) {
        out.println(data);
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void close() throws IOException {
        System.out.println("Exit connection");

        try {
            out.close();
        } finally {
            try {
                in.close();
            } finally {
                isExit = true;
                mainThread.interrupt();
                socket.close();
            }
        }
    }

    /*Set the current chat, info to server*/
    public void setChat(String chat) {
        JSONObject obj = new JSONObject();
        obj.put("name", c.getName());
        obj.put("message", "chat1");
        obj.put("from", c.getHost().toString());
        obj.put("to", "SERVER");
         try {
            SocketFactory.getSocketUtil().send(obj);
        } catch (IOException ex) {
           System.out.println(SoketMessages.ERROR_SEND);
        }
    }

}
