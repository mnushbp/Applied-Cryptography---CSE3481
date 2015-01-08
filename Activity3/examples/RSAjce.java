package examples ;


import java.security.*;
import javax.crypto.*;
import util.ByteWorks;
import java.security.interfaces.*;

public class RSAjce
{
	public static void main(String[] args) throws Exception
	{
		// ------------- Message must be shorter than size
		byte[] pt = "Hello".getBytes();

		// ------------- Java creates random keys
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(512);
		KeyPair pair = keyGen.generateKeyPair();
		PrivateKey priv = pair.getPrivate();
		PublicKey pub = pair.getPublic();

		// ------------- Use pkcs1 or no padding
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, pub);

		// ------------- Encrypt
		byte[] ct = cipher.doFinal(pt);
		System.out.println(ByteWorks.byteToHex(ct));

		// -------------- Decrypt for sanity
		cipher.init(Cipher.DECRYPT_MODE, priv);
		byte[] bk = cipher.doFinal(ct);
		System.out.println(new String(bk));

		// -------------- Peek at key material
		RSAPrivateCrtKey k = (RSAPrivateCrtKey) priv;
		System.out.println("p\t"+ k.getPrimeP());
		System.out.println("q\t"+ k.getPrimeQ());
		System.out.println("e\t"+ k.getPublicExponent());
		System.out.println("d\t"+ k.getPrivateExponent());
				
	}
}

