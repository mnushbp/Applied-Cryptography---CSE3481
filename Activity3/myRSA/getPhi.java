package myRSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class getPhi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//p,q, e,d, m,c 
		final BigInteger ONE = BigInteger.ONE;
		
		List<Integer> result = new ArrayList<Integer>() ; 
		
		int n = 55 ;
		BigInteger bn = new BigInteger("55");
		int count =0 ;
		
		for(int i=1; i <= n; i++ ){
			
			BigInteger bi = new BigInteger(String.valueOf(i));
		
			if (bn.gcd(bi).equals(ONE) ){
			//	System.out.println(bi.intValue());
				result.add(bi.intValue());
				count++;
			}
		}
		
		System.out.println("Phi value is "+count);
		
		// solve the equation and get p,q 
		BigInteger p = new BigInteger("11") ;
		BigInteger q = new BigInteger("5") ;
		
		//e=3
		//e= GCD(e,(p-1)(q-1)) =1
		//d ? 
		// 40 = dxe +1 
		// d= 27
		
		BigInteger e = new BigInteger("3") ;
		BigInteger m = new BigInteger("40");
		
		BigInteger d = e.modInverse(m);
		
		System.out.println("d is : "+d.intValue());
		

	}

}
