/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

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

    private String host;
    private Integer port;
    private String userName;
    
    private Socket socket;
    private long timeOutMilis = 5000;
    
    private BufferedReader in;
    private PrintWriter out;

    private boolean tryToReconnect = true;
    private boolean isExit = false;
    private Thread mainThread;
    
    public ClientSocket(String host, Integer port, String userName) throws IOException {
        this.host = host;
        this.port = port;
        this.userName = userName;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void connect() throws IOException {
 
        while (tryToReconnect) {
            System.out.println("Trying to connect...");
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host,port ));
                
                if (socket.isConnected()) {
                    tryToReconnect = false;               
                }

                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            JSONObject obj = new JSONObject();
             
            obj.put("name", this.userName);
            obj.put("message", "connected");
            obj.put("from", this.host.toString());
            obj.put("to", "SERVER");                //To the chat group??


            out.println(obj.toString());
        
            if (in != null) {
                System.out.println("[received] "+in.readLine() + "\n");
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
                    System.out.println("[received]: "+response);
                }
            } catch (IOException ex) {
                System.out.println("Socket disconected");
                try {
                    if(!isExit){
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
        }finally {
            try {
                in.close();
            } finally {
                isExit = true;
                mainThread.interrupt();
                socket.close();

            }
        }
    }

    public String getClientName() {
        return userName;
    }

    public void setClientName(String userName) {
        this.userName = userName;
    }
    

}
