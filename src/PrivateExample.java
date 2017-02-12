import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.*;
import javax.crypto.*;

//
// encrypt and decrypt using the DES private key algorithm
public class PrivateExample {
	public static void main (String[] args) throws Exception {
	//
		// check args and get plaintext
		if (args.length !=1) {
			System.err.println("Usage: java AESEncrypt plaintext.txt");
			System.exit(1);
		}
		
//		byte[] plainText = args[0].getBytes("UTF8");
		byte[] plainText = Files.readAllBytes(new File(args[0]).toPath());
		
		
		//
		// get a DES private key
		System.out.println( "\nStart generating AES key" );
//		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		Key key = keyGen.generateKey();
		System.out.println( "Finish generating AES key" );
		
		//
		// get a DES cipher object and print the provider
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		System.out.println( "\n" + cipher.getProvider().getInfo() );
		
		//
		// encrypt using the key and the plaintext
		System.out.println( "\nStart encryption" );
		cipher.init(Cipher.ENCRYPT_MODE, key);
		FileOutputStream cipherOut = new FileOutputStream("ciphertext.txt");
		byte[] cipherText = cipher.doFinal(plainText);
		cipherOut.write(cipherText);
		cipherOut.close();
		System.out.println( "Finish encryption: " );
		System.out.println( new String(cipherText, "UTF8") );
		
		//
		// decrypt the ciphertext using the same key
		System.out.println( "\nStart decryption" );
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		byte[] newPlainText = cipher.doFinal(cipherText);
		
		// write data to decrypted.txt
//		byte data[] = ...
		FileOutputStream decryptOut = new FileOutputStream("decryptedtext.txt");
		decryptOut.write(newPlainText);
		decryptOut.close();
	
		System.out.println( "Finish decryption: " );
		System.out.println( new String(newPlainText, "UTF8") );
	}
}