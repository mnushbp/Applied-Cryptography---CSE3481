package qu10;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import util.ByteWorks;

public class que10 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Key secret = new SecretKeySpec("universe".getBytes(), "DES");
		// no IV, our text is 64b
		Cipher ce = Cipher.getInstance("DES/ECB/NoPadding");
		ce.init(Cipher.ENCRYPT_MODE, secret);
		byte[] pt = "Facebook".getBytes();
		byte[] ct = ce.doFinal(pt);
		System.out.println("PT = " + ByteWorks.byteToHex(pt));
		System.out.println("CT = " + ByteWorks.byteToHex(ct));
		System.out.println("KY = " + new String(secret.getEncoded()));
		
		System.out.println("PT = " + ByteWorks.byteToBin(pt));
		System.out.println("CT = " + ByteWorks.byteToBin(ct));
		
		
		
		ce.init(Cipher.DECRYPT_MODE, secret);
		byte[] back = ce.doFinal(ct);
		System.out.println(new String(back));

	}

}
