/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import JCE.FirmaDigital;
import JCE.SimetricCrypter;
import Models.Client;
import Models.Message;
import java.io.*;
import java.net.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javamessenger.Screens.MenuScreen;
import javax.crypto.SecretKey;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    public MenuScreen delegate;

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
                JSONObject obj;
                try {
                    obj = new Message(SocketFactory.getSocketUtil().getClient().getName(),
                            SocketFactory.getSocketUtil().getClient().getHost(),
                            "connect",
                            Message.Type.ACTION,
                            SocketFactory.getSocketUtil().getClient().getHost(),
                            LocalDateTime.now(),
                            "").generateMessage();
                    SocketFactory.getSocketUtil().send(obj);
                } catch (IOException ex) {
                    System.out.println(SoketMessages.ERROR_SEND);
                }

                if (in != null) {
                    System.out.println("[received] " + in.readLine() + "\n");
                    
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        tryToReconnect = true;

    }

    public void listen() throws IOException {
        mainThread = new Thread(() -> {
            String response;
            try {
                while (socket.isConnected()) {
                    response = in.readLine();
                    System.out.println("[received]: " + response);
                    String TEXT;
                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject json = (JSONObject) parser.parse(response);
                        //TODO ENCRYPT
                        String data = json.get("data").toString();
                        String owner = json.get("owner").toString();
                        
                        
                        if(json.containsKey("public_key")) {
                            String public_key = json.get("public_key").toString();
                            String simetric_key = json.get("key").toString();
                            byte [] data_encrypted  = Base64.getDecoder().decode(json.get("data_encrypted").toString());
                            byte [] signature = Base64.getDecoder().decode(json.get("signature").toString());
                            
                            byte[] key =  Base64.getDecoder().decode(public_key);
                            
                            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(key));
                            
                            // System.out.println(publicKey);
                            SecretKey sk = SimetricCrypter.hashBuildKey(simetric_key);
                            byte[] textDecypt = SimetricCrypter.decrypt(sk, data_encrypted);
                            
                            if(FirmaDigital.validateSignature(textDecypt, signature, publicKey)) {
                                System.out.println("Firma valida");
                                data = new String(textDecypt);
                            }
   
                        }
                        
                        
                        this.delegate.appendMsg("room 1", owner, data);

                    } catch (ParseException ex) {
                        Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidKeySpecException ex) {
                        Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                    }

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
        System.out.println("send: " + data.toString());
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

        try {
            JSONObject obj = new JSONObject();
            obj = new Message(SocketFactory.getSocketUtil().getClient().getName(),
                    SocketFactory.getSocketUtil().getClient().getHost(),
                    "joined room 1",
                    Message.Type.ACTION,
                    SocketFactory.getSocketUtil().getClient().getHost(),
                    LocalDateTime.now(),
                    "room 1").generateMessage();

            SocketFactory.getSocketUtil().send(obj);

        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setInstance(MenuScreen Screen) {
        this.delegate = Screen;
    }

}
