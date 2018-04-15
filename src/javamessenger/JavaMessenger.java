/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamessenger;

import javamessenger.Screens.LoginScreen;

/**
 *
 * @author aaron
 */
public class JavaMessenger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("init");
        new LoginScreen().setVisible(true);
    }
    
}
