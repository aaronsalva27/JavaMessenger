/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamessenger.Screens;

import Models.Message;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import utils.SocketFactory;
import utils.SoketMessages;

/**
 *
 * @author aaron
 */
public class ChatScreen extends javax.swing.JFrame {

    /**
     * Creates new form ChatScreen
     */
    public ChatScreen(String room) {
        initComponents();
        System.out.println("Chat init");
        // Set JFrame to the center of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        try {
            //informar server cambio de chat1 de este usuario
            SocketFactory.getSocketUtil().setChat("room 1");
        } catch (IOException ex) {
            Logger.getLogger(ChatScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtChat1 = new javax.swing.JTextPane();
        tfMessage = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtChat1.setEditable(false);
        jScrollPane1.setViewportView(txtChat1);

        btnSend.setText("Enviar");
        btnSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSendMouseClicked(evt);
            }
        });
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tfMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfMessage)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
 
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnSendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendMouseClicked
        // TODO add your handling code here:
        JSONObject obj;
        String msg = tfMessage.getText();
        try {
            obj = new Message(SocketFactory.getSocketUtil().getClient().getName(),
                    SocketFactory.getSocketUtil().getClient().getHost(),
                    tfMessage.getText(),
                    Message.Type.MESSAGE,
                    SocketFactory.getSocketUtil().getClient().getHost(),
                    LocalDateTime.now(),
                    "room 1").generateMessage();

            appendMsg("room 1", SocketFactory.getSocketUtil().getClient().getName(), msg);
            tfMessage.setText("");
            SocketFactory.getSocketUtil().send(obj);
        } catch (IOException ex) {
            System.out.println(SoketMessages.ERROR_SEND);
        }
        
    }//GEN-LAST:event_btnSendMouseClicked

    private void appendMsg(String chat, String clientName, String msg) {

        switch (chat) {
            case "room 1":
                txtChat1.setText(txtChat1.getText() + "\n"
                        + "[" + clientName + "]: " + msg);
                break;
            case "chat2":
                System.out.println("manolo programa un poco");
                break;
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tfMessage;
    private javax.swing.JTextPane txtChat1;
    // End of variables declaration//GEN-END:variables
}
