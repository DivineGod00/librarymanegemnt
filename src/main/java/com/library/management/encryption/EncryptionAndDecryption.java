package com.library.management.encryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptionAndDecryption {

	
//	@Value("${secret.key}")
	private static String key = "{O()-@345-#$%@!-";
	
	
	private static String encryption(String encryptString, String key)throws Exception
	{
		
		SecretKey secretKey = secretKey(key);

        // Initialize the Cipher object with encryption mode
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt the message
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

        // Encode the encrypted byte array into a base64 string
        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedData);
        
        return encryptedMessage;

	}
	
	private static String decryption(String encryptStr,String key)throws Exception
	{
		byte[] encryptedData = Base64.getDecoder().decode(encryptStr);

        // Generate the SecretKey using the given key
        SecretKey secretKey = secretKey(key);

        // Initialize the Cipher object with decryption mode
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Decrypt the message
        byte[] decryptedData = cipher.doFinal(encryptedData);
        
        String decryptedMessage = new String(decryptedData);
        
        return decryptedMessage;
        
	}

	private static SecretKeySpec secretKey(String key)throws Exception
	{
		 return new SecretKeySpec(key.getBytes(), "AES");
	}
	
	public static String encryptStr(String passwordStr)throws Exception
	{
//		String key = "{O()-@345-#$%@!-";
		return encryption(encodeToShortString(passwordStr),key);
		
	}

	public String decryptStr(String encryptStr)throws Exception
	{
//		String key = "{O()-@345-#$%@!-";
		String decrypt = decryption(encryptStr,key);
		return getBase64DecodedString(decrypt);
	}
	
	
	
	
	private static String encodeToShortString(String originalString) {
        byte[] encodedBytes = Base64.getEncoder().encode(originalString.getBytes());
        return new String(encodedBytes);
    }
 
	private static String getBase64DecodedString(String base64EncodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedString.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes, StandardCharsets.UTF_8);
   }

}
