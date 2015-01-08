package examples ;

import java.math.BigInteger;
import java.util.Random;

/**********************************************
Demonstrate how RSA works using ~small numbers. 
Enable assertion for the sanity checks to work.
**********************************************/
public class RSAdemo
{

	public static void main(String[] args)
	{
		final int CERTAINTY = 20;
		final int BIT_SIZE = 20;
		final BigInteger ONE = BigInteger.ONE;
		BigInteger p = new BigInteger("5");
		BigInteger q = new BigInteger("11");
		// BigInteger q = new BigInteger(BIT_SIZE, CERTAINTY, new Random());
		assert p.isProbablePrime(CERTAINTY): "p not prime!";
		assert q.isProbablePrime(CERTAINTY): "q not prime!";
		BigInteger n = p.multiply(q);
		BigInteger h = p.subtract(ONE).multiply(q.subtract(ONE));
		
		BigInteger e = new BigInteger("3");
		assert e.gcd(h).equals(ONE): "e not coprime with phi";
		BigInteger d = e.modInverse(h);
		assert e.multiply(d).mod(h).equals(ONE);
		
		System.out.println("p = " + p);
		System.out.println("q = " + q);
		System.out.println("n = " + n);
		System.out.println("h = " + h);
		System.out.println("e = " + e);
		System.out.println("d = " + d);
		System.out.println("Our RSA bit size is: " + n.bitCount());
		
		String msg = "!";
		BigInteger m = new BigInteger(msg.getBytes());
		assert m.compareTo(n) == -1 : "m is too big /bits = " + m.bitCount();
		
		BigInteger ct = m.modPow(e, n);
		System.out.println("ct\t" + ct);
		BigInteger bk = ct.modPow(d, n);
		System.out.println("bk\t" + bk);
		System.out.println("as a string\t" + new String(bk.toByteArray()));
	}

}
