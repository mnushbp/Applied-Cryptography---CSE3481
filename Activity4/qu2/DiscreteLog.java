package qu2;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Manusha
 *
 */
public class DiscreteLog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		BigInteger beta = new BigInteger(args[0]);
		BigInteger p = new BigInteger(args[1]);
		BigInteger alpha  = new BigInteger(args[2]);
		BigInteger q ;
		
		
		/*
		BigInteger beta = new BigInteger("210");
		BigInteger p = new BigInteger("251");
		BigInteger alpha  = new BigInteger("71");
		BigInteger q ;
		
		*/
		
		System.out.println("g is : "+alpha.intValue());
		System.out.println("p is : "+p.intValue());
		System.out.println("Beta is : "+beta.intValue());
		System.out.println();
		
		Map<Integer,Integer> pMap = getPrime(p.intValue()-1) ; //Prime number map 	
		Map<Integer,Integer> eMap = new HashMap<Integer, Integer>();  //Equation value map 
		
		for (Map.Entry<Integer, Integer> pri : pMap.entrySet()) { 		
			
			q = new BigInteger(String.valueOf(pri.getKey()));
			int count = pMap.get(pri.getKey());
			
			System.out.println("Prime factors :"+q.intValue() + "^"+count);
			
			int x0 = getX(beta, alpha, q, p, new BigInteger(String.valueOf(q)));
			System.out.println("x0 is "+x0);
			
			
			//When prime factors are repeated 
			
			int oldx = x0;
			int totalX = x0; // keeps a track of x=x0+x1+..... 
		
			int i =1;
			BigInteger tempbeta= beta;
			
			for(; i < count; i++){
				
				BigInteger newBeta = tempbeta.multiply(alpha.modInverse(p).modPow(new BigInteger(String.valueOf(oldx)).multiply(q.pow(i-1)), p)).mod(p);
				
				int x1 = getX(newBeta, alpha, q.pow(i+1), p,q);
				System.out.println("x"+i+" is " +x1);	
				
				totalX = totalX + (int) (Math.pow(q.intValue(), i)*x1);
				oldx=x1;
				tempbeta= newBeta;
			}	
			System.out.println("X = " +totalX+ " mod "+ (int) Math.pow(q.intValue(), count)+" \n" );	
			eMap.put(totalX, (int) Math.pow(q.intValue(), count));
		}
		
		BigInteger[] residue = new BigInteger[eMap.size()]; 
		BigInteger[] modulus = new BigInteger[eMap.size()] ; 
		int temp = 0;
		
		for (Map.Entry<Integer, Integer> eq : eMap.entrySet()) {
			
			residue[temp]= new BigInteger(String.valueOf(eq.getKey()));
			modulus[temp] = new BigInteger(String.valueOf(eMap.get(eq.getKey())));			
			
			temp++;		
		}
		
		BigInteger[] result = solveCRT(residue, modulus);
		
		System.out.println("Using CRT : "); 
		System.out.println("x = " + result[0] + " mod " + result[1]);	
	}
	
	//to obtain x values 
	private static int getX(BigInteger b, BigInteger a, BigInteger q, BigInteger p, BigInteger iq){
		
		BigInteger lhs = b.modPow(p.subtract(BigInteger.ONE).divide(q),p);
		BigInteger rhs = a.modPow(p.subtract(BigInteger.ONE).divide(iq),p);
	
		int xValue = 0 ;
		for (int j =0 ; j <= q.intValue()-1 ; j++ ){
			if(lhs.equals(rhs.modPow(new BigInteger(String.valueOf(j)),p))){
				xValue= j;
				break;
			}
		}
		return xValue;
	}
	
	//to obtain prime numbers 
	private static Map<Integer,Integer>  getPrime(int number){
		Map<Integer,Integer> pMap = new HashMap<Integer, Integer>();
		
		for (int i = 2; i <= number; i++) {
			if (number % i == 0) {
				
				if(pMap.containsKey(i)){
					int temp = pMap.get(i);
					pMap.put(i, (temp+1));
				}
				else{
					pMap.put(i, 1);
				}
				 number /= i;
				 i--;
			}
		}
		return pMap;
	}
	
	
	
//-------------------------------------------------------------------------------------------------------------------	
	// Obtained from http://www.csee.umbc.edu/~stephens/crypto/CIPHERS/BigIntegerMath.java
	public static BigInteger[] solveCRT(BigInteger[] residue,
			BigInteger[] modulus) {
		if (residue.length != modulus.length)
			throw new IllegalArgumentException(
					"Residues and moduli are in different amounts.");

		for (int i = 0; i < modulus.length - 1; i++) {
			for (int j = i + 1; j < modulus.length; j++) {
				if (!(modulus[i].gcd(modulus[j]).equals(BigInteger.ONE)))
					throw new IllegalArgumentException(
							"Moduli are not pairwise relatively prime.");
			}
		}

		BigInteger M = new BigInteger("1");
		for (int i = 0; i < modulus.length; i++)
			M = M.multiply(modulus[i]);
		// Form the solution as in Chinese Remainder Theorem
		BigInteger solution = new BigInteger("0");
		for (int i = 0; i < modulus.length; i++) {
			BigInteger Mi = M.divide(modulus[i]);
			solution = solution.add(residue[i].multiply(Mi).multiply(
					Mi.modInverse(modulus[i])));
		}
		solution = lnr(solution, M);
		// Answer must be returned as a two dimensional array.
		BigInteger[] result = new BigInteger[2];
		result[0] = solution;
		result[1] = M;
		return result;
	}

	// Computes the least nonnegative residue of b mod m, where m>0.
		public static BigInteger lnr(BigInteger b, BigInteger m) {
		if (m.compareTo(BigInteger.ZERO) <= 0)
			throw new IllegalArgumentException("Modulus must be positive.");
		BigInteger answer = b.mod(m);
		return (answer.compareTo(BigInteger.ZERO) < 0) ? answer.add(m) : answer;
	}
}
