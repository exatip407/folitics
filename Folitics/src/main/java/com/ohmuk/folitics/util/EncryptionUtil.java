package com.ohmuk.folitics.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptionUtil {
    protected static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtil.class);

    private static KeyGenerator keyGenerator;
    private static SecretKey secretKey;
    private static Cipher cipher;

    static {
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            secretKey = keyGenerator.generateKey();
            cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            new Throwable(e.getMessage());
        }
    }

//    public static void main(String[] args) throws Exception {
//
//        String plainText = "AES Symmetric Encryption Decryption";
//        System.out.println("Before Encryption: " + plainText);
//
//        String encryptedText = encrypt(plainText);
//        System.out.println("Encrypted        : " + encryptedText);
//
//        String decryptedText = decrypt(encryptedText);
//        System.out.println("After Decryption : " + decryptedText);
//    }

    private static String encrypt(String plainText, SecretKey secretKey, Cipher cipher) throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    private static String decrypt(String encryptedText, SecretKey secretKey, Cipher cipher) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public static String encrypt(String plainText) throws Exception {
        return encrypt(plainText, secretKey, cipher);
    }

    public static String decrypt(String encryptedText) throws Exception {

        return decrypt(encryptedText, secretKey, cipher);
    }

    public static final String getEncryptedUserId(Long id){
        try {
            return EncryptionUtil.encrypt(String.valueOf(id));
        } catch (Exception e) {
            LOGGER.error("Encryption failed for id : "+id);
        }  
        return null;
    }
    public static final Long getDecryptedUserId(String encryptedId){
        try {
           return Long.getLong(EncryptionUtil.decrypt(encryptedId));
        } catch (Exception e) {
            LOGGER.error("Encryption failed for string : "+encryptedId);
        }  
        return null;
    }
 
}
