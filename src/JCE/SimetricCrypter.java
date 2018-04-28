/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JCE;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author davrami
 */
public class SimetricCrypter {
    public static final String KEY_ALGORITHM = "AES";
    public static final String PASSWORD_HASH_ALGORITHM = "SHA-1";
    
    public SimetricCrypter() { }
    
    public static SecretKey hashBuildKey(String password) {
        
        SecretKey spec = null;
        try {
            MessageDigest digester = MessageDigest.getInstance(PASSWORD_HASH_ALGORITHM);
            digester.update(password.getBytes("UTF-8"));
            byte[] hash = digester.digest();            
            byte[] key = Arrays.copyOf(hash, 16);
            spec = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return spec;
    }
    
    public static byte[] encrypt(SecretKey sk, String data) {
            Cipher desCipher;
            byte[] textEncrypted = null;
        try {
            desCipher = Cipher.getInstance("AES");
            desCipher.init(Cipher.ENCRYPT_MODE, sk);
            // Xifrem el text introduït per l'usuari
            textEncrypted = desCipher.doFinal(data.getBytes());
            System.out.println("Resultat encriptat: \t" + getStringFromByteArray(textEncrypted));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IllegalBlockSizeException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return textEncrypted;       
    }
    
    public static byte[] decrypt(SecretKey sk, byte[] data) {
        byte[] textDecrypted = null;
        try {
            // Instantiate the cipher
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, sk);

            //byte[] encryptedTextBytes = new Base64().decodeBase64(encryptedText);
            textDecrypted = cipher.doFinal(data);
            System.out.println("Resultat decrypt: \t"+ new String(textDecrypted));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IllegalBlockSizeException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(SimetricCrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return textDecrypted;
    }
    
    public static String getStringFromByteArray(byte[] b){
		String text = "";
		try {
			BigInteger number = new BigInteger(1, b);
            text = number.toString(16);
            while (text.length() < 16) {
                text = "0" + text;
            }
		}
		catch(Exception e){}
		return text;
	}
    
    
}
