package qu9;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream.GetField;
import java.util.Random;

import util.LetterWorks;

public class qu9b {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		    String file  = "./textfile" ;
			//String file  = "./Sh1949" ;
			
			
			try {
				byte[] b =  util.ByteWorks.fileToBytes(file+".pt");
			
				//Observe Freequancies ----------------------
				int[] fArray =  util.LetterWorks.getFrequencies(b);
				char c = 'A'; 
				for(int i=0 ; i < fArray.length; i ++ ){
					System.out.println(c+"  "+fArray[i]);
					c++ ;
				}
				
				//Caesar Cipher----------------------
				int key= 5 ;
				byte [] cArray = b;
				for(int i=0 ; i < b.length; i ++ ){
					
					
					cArray[i] =  (byte) ((b[i]-65 + key) %26)   ; 
					cArray[i] = (byte) (cArray[i] + 65) ; 
					//System.out.println(b[i]+"  "+cArray[i]);
				}
				util.ByteWorks.bytesToFile(cArray, file+".ct" );
				
				
				//decryption 
				
				for(int i=0 ; i < b.length; i ++ ){
					
					
					cArray[i] =  (byte) ((b[i]-65 + key) %26)   ; 
					cArray[i] = (byte) (cArray[i] + 65) ; 
					//System.out.println(b[i]+"  "+cArray[i]);
				}
				util.ByteWorks.bytesToFile(cArray, file+".ct" );
				
				
				
				//Observe Freequancies in CT---------
				byte[] ct =  util.ByteWorks.fileToBytes(file+".ct");
				fArray =  util.LetterWorks.getFrequencies(ct);
				c = 'A'; 
				for(int i=0 ; i < fArray.length; i ++ ){
					System.out.println(c+"  "+fArray[i]);
					c++ ;
				}
				
				//YORK HASH ----------------------------
				 int sum[] = new int[16]; 
				 byte hash[] = new byte[16]; 
				   
				 int i ; 
				 int row = 0  ;
				
				 for(int x=0 ; x < b.length ; x++ )
				 {
					  i=0;
					  while(i < 16){
						  try{
							  sum[i] =  sum[i] + b[i+row] ;   
						   }catch(ArrayIndexOutOfBoundsException e){
							   sum[i] =  sum[i] + 0 ;   //If length is less   
						   }
						  i++;
					   }
					  if( (row+i) > b.length && (row+i)%16==0){
						  break; 
					  }
					  else 
					   row=row + 16 ;  
				   }
				   
				   i=0;
				   while (i<16) // create hash 
				   {
					   sum[i] = sum[i] % 26 ; 					
					   hash[i] = (byte) (sum[i]+65) ; 			
					   i++; 
				   }
				   util.ByteWorks.bytesToFile(hash, "./yhash.pt" );
   
				   
				   //Compute probability ENGLISH-----------------------
				   
				    Random rng = new Random();
					int text = 1000000;
					int same = 0;
					for (int draw = 0; draw < text; draw++)
					{
						int c1 =  (int) (rng.nextDouble()*1000);
						int  c2 =  (int) (rng.nextDouble()*1000);
						
						if (b[c1] == b[c2])
							same++;
					}
					double ic = same / (double) text ;
					System.out.println("Engligh IC is :"+ic);
					
					
					 //Compute probability RANDOM-----------------------
						text = 1000000;
						same = 0;
						int randomArr[] = new int[26]; 
						for(i=0; i <26; i++){
							randomArr[i]= i+65 ;
						}
						for (int draw = 0; draw < text; draw++)
						{
							int c1 =  (int) ((rng.nextDouble()*1000)%26);
							int  c2 =  (int) ((rng.nextDouble()*1000)%26);
							
							if (randomArr[c1] == randomArr[c2])
								same++;
						}
						ic = same / (double) text ;
						System.out.println("Random IC is :"+ic);
				   
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("exception");
			}
			
			
		

	}
	
	
	

}
