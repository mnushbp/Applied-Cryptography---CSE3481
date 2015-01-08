package qu9;

public class caesarDycript {

	public static void main(String[] args) {
	
		// TODO Auto-generated method stub
		decrypt();

	}

	
	
	public static void  decrypt(){
		
		String s = "BYHNGWRHNKIETBGMXQM" ;
		byte[] bArray = s.getBytes() ;
		byte[] plain = new byte[bArray.length] ; 
		int k= 0 ;  int i=0 ;
		char c ; 
		while (i < 26)
		{
			for(k=0 ; k < bArray.length ; k++)
			{
				plain[k] =  (byte) ((byte) ((bArray[k] - i)%26) +65)  ; 
				//c= (char) plain[k];
				//System.out.println(c+" k "+k+" i "+i);
			}
			
			for(int x=0 ; x < plain.length; x ++ ){
				c= (char) plain[x];
				System.out.print(c);
			}
			System.out.println();
			i++; 
		
		}
	}
	
}
