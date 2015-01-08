package qu11;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.ByteWorks;

public class qu11 {

	/**
	 * @param args
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidAlgorithmParameterException 
	 */
	public static void main(String[] args) throws Exception {
		
		Key secret = new SecretKeySpec(ByteWorks.hexToByte("9F0DCEDB322F3C6873F9256E01376BA4"), "AES");
		
		AlgorithmParameterSpec aps = new IvParameterSpec(ByteWorks.hexToByte("20FC19123087BF6CAC8D0F1254123004"));
		byte[] ct = ByteWorks.hexToByte("F38ADBA8A7B4CC613578355032205D50"); ;
		
		Cipher ce = Cipher.getInstance("AES/CBC/PKCS5Padding");
	
		System.out.println("CT = " + ByteWorks.byteToHex(ct));
		System.out.println("KY = " + new String(secret.getEncoded()));
		System.out.println("CT = " + ByteWorks.byteToBin(ct));
		
		ce.init(Cipher.DECRYPT_MODE, secret,aps);
		byte[] back = ce.doFinal(ct);
		System.out.println(new String(back));
		
	}

}
