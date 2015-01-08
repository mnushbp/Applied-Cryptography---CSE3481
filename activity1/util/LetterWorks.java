package util;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Random;

public class LetterWorks {
	
	/** Empty to prevent instantiation */
	   private LetterWorks()
	   {	   
	   }

	   /**
	    * Clean away all the non-letters from an ANSI byte array and return a new
	    * byte array with letters converted to upper-case.
	    **/
	   public static byte[] clean(byte[] in)
	   {
		   ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		   
		   /*
		   byte[] output = new byte[in.length]; 
		   int idx = 0; 
		   for( byte c : in) { 
			   if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z') { 
				   if (c >= 'a')
					   c -= 0x20; 
				   output[idx++] = c; 
				   } 
			   } 
		   return Arrays.copyOf(output, idx);
		   */
		   
		 //  System.out.println(in.length); 
		   
		   int c=0; 
		   for(Byte b : in)
		   {		   
			   if(b >= 'a' && b <='z')
			   {
				   b=(byte) (b-32); 
			   }
			   
			   if(b >= 'A' && b <= 'Z' )
			   {
				   baos.write((byte) b); 
				   c++ ;
			   }  
			  
		   }
		  // System.out.println(c); 
		   return baos.toByteArray();
		   
		   
	   }
	   /**
	    * Compute the frequencies of letters in a byte array made up of caps
	    **/
	   public static int[] getFrequencies(byte[] ar)
	   {
		   int fArray[] = new int[26];
		   
		   for(int i=0; i<ar.length; i++)
		   {   
			   fArray[ar[i]-65]= fArray[ar[i]-65]+1;
		   }
		   
		   return fArray;   
	   }
	   /**
	    * Compute the hash of an array of bytes via xor every 16
	    **/
	   public static byte[] getXorHash(byte[] ar)
	   {
		   byte fArray[] = new byte[16]; 
		   
		   int i ; 
		   int row = 1  ;
		   
		   for(int x=0 ; x <= ar.length ; x++ )
		   {
			   i=1;
			   while(i <= 16){
				   
				   try{
					   fArray[i] = (byte) (fArray[i] ^ ar[i+row]) ;
					   i++;
					   
				   }catch(Exception e){
					   fArray[i] = (byte) (fArray[i] ^ 0) ;   //If length is less 
					   i++;
				   }
			   }
			   row=row + 16 ;
			   
		   }
		   return fArray ;
	   }
	   /**
	    * Compute the hash of an array of bytes via ascii sum mod 26 every 16
	    **/
	   public static byte[] getSumHash(byte[] ar)
	   {
		   byte fArray[] = new byte[16];
		   
		   int i =0; 
		   int row = 0  ; 
		   
		   for(int x=0 ; x < ar.length ; x++ )
		   {
			   while(i < 16){
				   fArray[i] = (byte) (fArray[i] + ar[i+row]) ;
			   }
			   row=row + 16 ; 
		   }
		   return fArray ;
	   }
	   /**
	    * Compute Index of Coincidence via frequencies
	    **/
	   public static double getFreqIC(byte[] ar)
	   {

			int[] frequencies = getFrequencies(ar);
			int n = ar.length;
			double ic = 0;
			for(int i : frequencies) {
			ic += (((double)i*((double)i+1))/((double)n*((double)n-1)));
			}
			return ic;
	   }
	   /**
	    * Compute Index of Coincidence via Monte Carlo
	    **/
	   public static double getMonteIC(byte[] ar, int draws)
	   {
		   	Random rng = new Random();
			int same = 0;
			for (int draw = 0; draw < draws; draw++)
			{
				int c1 =  (int) (rng.nextDouble()*1000);
				int  c2 =  (int) (rng.nextDouble()*1000);
				
				if (ar[c1] == ar[c2])
					same++;
			}
			double ic = same / (double) draws ;
			System.out.println("Engligh IC is :"+ic);
			return ic ;
	   }
}
