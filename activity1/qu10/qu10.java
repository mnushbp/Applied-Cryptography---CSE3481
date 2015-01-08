package qu10;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class qu10 {

	/**
	 * @param args 
	 *  -- not complete -- 
	 */
	public static void main(String[] args) {
		
		//----Find KeyLength 
		 String file  = "./C1" ;
		 try {
			byte[] b =  util.ByteWorks.fileToBytes(file+".txt");
			int keyLength = getKeyLength(b) ;
			System.out.println("Key Length is : "+keyLength);
			if(keyLength != -1){
				int f[] = util.LetterWorks.getFrequencies(b);
				int maxF; 
				int k[] = new int[keyLength];
				
				//hightest f is e and second highest is 
				
				for (int x=0; x<keyLength ; x++){
					
					maxF=0; 
					for(int i = 0 ; i < f.length ; i++)
					{
						if(maxF<f[i]){
							maxF=f[i];
							k[x]=i-4; //E 
						}
						else{
							if(k[1]<f[i])
								k[1]=i-19;  //T
						}
					}
				}
				//System.out.println(maxF+" "+k);
				for(int i = 0 ; i < k.length ; i++)
				{
					char key = (char) (k[i]+65);
					//System.out.println(key);
				}
				
			}
			else{
				System.out.print("Error! Not ENGLISH");
			}
			
			for(int x=0; x+1 < b.length ; x=x+2)
			{
			   b[x] = (byte) (b[x]+4);
			   b[x+1] = (byte) (b[x+1]+6);
			}
			
			util.ByteWorks.bytesToFile(b, "./de.txt" );	
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	private static int getKeyLength(byte[] b){
		
		int keylen = 1; 
		double ic = 0;
		
		
		while (keylen<b.length){
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			for(int i=0; i < b.length ;i=i+keylen){
				baos.write(b[i]);
			}
			
			ic= util.LetterWorks.getFreqIC(baos.toByteArray()); 
		//	
			if(ic>0.06)
				return keylen; 
			else
				keylen++;
		}
		return -1;
	
	}

}
