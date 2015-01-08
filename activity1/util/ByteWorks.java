package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteWorks {
	

	   /** Empty to prevent instantiation */
	   private ByteWorks()
	   { 
	   }

	   /** 
	    * Read the given file into an array of bytes 
	    **/
	   public static byte[] fileToBytes(String filename) throws Exception
	   {
		   FileInputStream fis = new FileInputStream(filename);
		   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   
		   int r  = fis.read() ;
		   while(r != -1){
			
			   baos.write(r) ;
			   r=fis.read() ;
			  
		   }
		   fis.close();
		   return baos.toByteArray(); 
		   
		   
		   /*
		   int x;
		   do {
			   x = fis.read();
			   if (x != -1) baos.write((byte) x);
		   	} while (x != -1);
		   fis.close();
		   return baos.toByteArray();
 			*/
	   }

	   /** 
	    * Create a file with the given name and wWrite the given array of bytes to it 
	    **/ 
	   public static void bytesToFile(byte[] data, String filename) throws Exception
	   {
		   FileOutputStream fos = new FileOutputStream(filename);
		   fos.write(data);
		   fos.close();
	   }
	   
	   public static String byteToHex(byte[] ar)
		{
			assert ar != null;
			String result = "";
			for (int i = 0; i < ar.length; i++)
			{
				int x = ar[i] & 0x000000FF;
				String tmp = Integer.toHexString(x);
				if (x < 16) tmp = "0" + tmp;
				result += tmp;
			}
			return result.toUpperCase();
		}
		
		public static byte[] hexToByte(String hex)
		{
			assert hex != null && hex.length() % 2 == 0;
			byte[] result = new byte[hex.length() / 2];
			for (int i = 0; i < hex.length(); i = i + 2)
			{
				result[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			}
			return result;
		}
		
		public static String byteToBin(byte[] data) {
			
			String s1 = " "; 
			for(byte b  :data){
			s1 = s1.concat(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
			}
			
			return s1; 
		}

}
