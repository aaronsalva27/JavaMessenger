/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import Models.Client;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author aaron
 */
public class SocketFactory {

    private static ClientSocket socketUtil = null;
    private static Client clientUtil = null;
    
    
    public SocketFactory(Client c) {
        clientUtil = c;
    }
    
    public static void setSocketUtil(ClientSocket cs ) {
        socketUtil = cs;
    }
    
    public static ClientSocket getSocketUtil() throws IOException {
        if (socketUtil == null) {
            socketUtil = new ClientSocket(clientUtil);
        }
        return socketUtil;
    }
    
}
