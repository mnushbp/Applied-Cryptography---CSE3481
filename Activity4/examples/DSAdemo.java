package examples;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.*;

public class DSAdemo
{

	public static void main(String[] args) throws Exception
	{
		String pt = "Hello this is a DSA example";
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
		SecureRandom rng = new SecureRandom();
		keyGen.initialize(512, rng);
		KeyPair pair = keyGen.generateKeyPair();
		PublicKey pub = pair.getPublic();
		PrivateKey prv = pair.getPrivate();
		Signature dsa = Signature.getInstance("SHA1withDSA");
		dsa.initSign(prv); 
		dsa.update(pt.getBytes()); 
		byte[] signature = dsa.sign(); // this encompasses r and s
		//----> Send the pt (possibly encrypted) and signature
		
		// Upon receipt, Bob does this:
		dsa.initVerify(pub); 
		dsa.update(pt.getBytes()); 
		System.out.println(dsa.verify(signature));
		
		//-------------------Let's look under the hood
		DSAPublicKey pub2 = (DSAPublicKey) pub;
		DSAPrivateKey prv2 = (DSAPrivateKey) prv;
		
		BigInteger y = pub2.getY();
		BigInteger g = pub2.getParams().getG();
		BigInteger q = pub2.getParams().getQ();
		BigInteger p = pub2.getParams().getP();
		BigInteger x = prv2.getX();
		
		System.out.println("g^x % p \t" + g.modPow(x, p));
		System.out.println("beta/Y  \t" + y);
		System.out.println("p mod q \t" + p.subtract(BigInteger.ONE).mod(q));
		System.out.println("q prime?\t" + q.isProbablePrime(20));
		System.out.println("g^q %p  \t" + g.modPow(q, p));
		
		//-------------------Manual DSA
		// Given the above, we can generate a random k and do
		// the r/s computation ourselves.
 
	}
}
