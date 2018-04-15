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
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String userName;
    
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
        try {
            socket = new Socket(host, port);

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // out.println("Hola");
            JSONObject obj = new JSONObject();
            obj.put("name", this.userName);
            obj.put("message", "connected");
            out.println(obj);
            
            System.out.println(in.readLine() + "\n");

        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listen() throws IOException {
        
        
        new Thread(() -> {
            String response;
            
            try {
                while (true) {
                   response = in.readLine();
                   System.out.println(response);
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }).start();
    }
    
    
    public<T> void send(T data) {
        out.println(data);
    }
            
    
    public void close() throws IOException {
        socket.close();
    }
            
    
    
    
    
}
