package qu9;

public class qu9 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		  String file  = "./textfile" ;
		 //String file  = "./Sh1949" ;
		 //System.out.println("TESTTET");
	
			byte[] b =  util.ByteWorks.fileToBytes(file+".txt");
			
			byte[] c = util.LetterWorks.clean(b); 
			//System.out.println(b.length);	
			//System.out.println(new String(c));
			util.ByteWorks.bytesToFile(c, file+".pt" );	
		    
		
		

	}

}
