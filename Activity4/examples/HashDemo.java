package examples;

import java.security.*;

public class HashDemo
{
	public static void main(String[] args) throws Exception
	{
		byte[] msg = "Hello there".getBytes();
		MessageDigest md = MessageDigest.getInstance("Sha-384");
		byte[] digest = md.digest(msg);
		System.out.println(util.ByteWorks.byteToHex(digest).length());
		System.out.println(util.ByteWorks.byteToHex(digest));
		System.out.println(util.ByteWorks.byteToBin(msg));
		
		System.out.println(util.ByteWorks.byteToBin(digest).length());
	}
}
