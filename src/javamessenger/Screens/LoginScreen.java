package javamessenger.Screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import utils.NameGenerator;

public class LoginScreen extends javax.swing.JFrame {

    public LoginScreen() {
        initComponents();
        // Set JFrame to the center of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        tfName.setText(NameGenerator.generateName());   //Generate random name Clas in utils
        tfHost.setText("127.0.0.1");                    //Host server
        tfPuerto.setText("6969");                       //Port listening server
        
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTest = new javax.swing.JButton();
        tfName = new javax.swing.JTextField();
        lbName = new javax.swing.JLabel();
        tfHost = new javax.swing.JTextField();
        lbHost = new javax.swing.JLabel();
        tfPuerto = new javax.swing.JTextField();
        lbPuerto = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnTest.setText("Button");
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });

        lbName.setText("Username: ");

        lbHost.setText("Host: ");

        tfPuerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfPuertoKeyTyped(evt);
            }
        });

        lbPuerto.setText("Puerto:");

        lbTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("WELCOME");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbName)
                    .addComponent(lbHost)
                    .addComponent(lbPuerto)
                    .addComponent(tfName)
                    .addComponent(tfHost)
                    .addComponent(tfPuerto)
                    .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(btnTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lbName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbHost, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(lbPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnTest)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Method that check is all field are full and open a new screen
     * @param evt 
     */
    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed
        Boolean isValid  = true;
        
        if (tfName.getText().isEmpty()) {
            tfName.setBackground(Color.RED);
            isValid = false;
        } else {
            tfName.setBackground(Color.GREEN);
        }
        if (tfHost.getText().isEmpty()){
            tfHost.setBackground(Color.RED);
            isValid = false;
        } else {
            tfHost.setBackground(Color.GREEN);
        }
        if (tfPuerto.getText().isEmpty()){
            tfPuerto.setBackground(Color.RED);
            isValid = false;
        } else {
            tfPuerto.setBackground(Color.GREEN);
        }
        
        System.out.println("[clientName] "+tfName.getText() + " - " +
                           "[host] "+ tfHost.getText()+ " - " +
                           "[port] "+tfPuerto.getText());
        if(isValid) 
            try {
                new MenuScreen(tfName.getText(),tfHost.getText(),tfPuerto.getText()).setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        else    
            System.out.println("NO");
        
    }//GEN-LAST:event_btnTestActionPerformed

    /**
     * Method that checks if the key typed is a number.
     * @param evt 
     */
    private void tfPuertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPuertoKeyTyped
        char vChar = evt.getKeyChar();
                    if (!(Character.isDigit(vChar)
                            || (vChar == KeyEvent.VK_BACK_SPACE)
                            || (vChar == KeyEvent.VK_DELETE))) {
                        evt.consume();
                    }
    }//GEN-LAST:event_tfPuertoKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTest;
    private javax.swing.JLabel lbHost;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbPuerto;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTextField tfHost;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfPuerto;
    // End of variables declaration//GEN-END:variables
}
