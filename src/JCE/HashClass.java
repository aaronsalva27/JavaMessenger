/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JCE;

import java.security.MessageDigest;

/**
 *
 * @author davrami
 */
public class HashClass {
    private String key;
    
    public HashClass(String key) {
        this.key = key;
    }
    
    //Calcul de la suma d'una String en byte[]
    public byte[] sumCalc (String type){
        byte[] hashedKey = null;
        try {
            byte [] byteKey = key.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance(type);
            hashedKey = md.digest(byteKey);
        }catch (Exception ex){
            System.err.println("Error generant clau" + ex);
        }
        return hashedKey;
    }
}
