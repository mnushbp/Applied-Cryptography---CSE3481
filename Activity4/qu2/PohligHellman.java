package qu2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.*;

public class PohligHellman {
	public static final BigInteger ONE = BigInteger.ONE;
	public static final BigInteger ZERO = BigInteger.ZERO;

	public PohligHellman() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		BigInteger p, g, beta;

		p = new BigInteger("251");
		g = new BigInteger("71");
		beta = new BigInteger("210");

		BigInteger order = p.subtract(ONE);
		System.out.println("p\t:\t" + p);
		System.out.println("g\t:\t" + g);
		System.out.println("beta\t:\t" + beta);
		System.out.println("order\t:\t" + order);

		PohligHellman main = new PohligHellman();
		// Prime factors of p-1
		Map<Integer, Integer> factors = main.primeFactors(order);

		// These two lists are needed for CRT function at the very end.
		// The lists store computed X value for each 'q' and their corresponding
		// mod
		List<BigInteger> finalX = new ArrayList<BigInteger>();
		List<BigInteger> finalMod = new ArrayList<BigInteger>();

		for (Map.Entry<Integer, Integer> e : factors.entrySet()) {
			
			BigInteger q = new BigInteger(Integer.toString(e.getKey()));
			int r = e.getValue();
			BigInteger pminus1byq = order.divide(q);
			BigInteger beta_pminus1byq = beta.modPow(pminus1byq, p);
			
			System.out.print("q=" + e.getKey() + "\tr=" + e.getValue()
					+ "\tp^r=" + q.pow(r) + "\t(p-1/q)=" + pminus1byq
					+ "\tb^(p-1/q)(modp)=" + beta_pminus1byq + "\t");

			BigInteger g_pminus1byq = g.modPow(pminus1byq, p);

			// Start generating x_0, x_1, etc...
			List<BigInteger> equationXValues = new ArrayList<BigInteger>();
			// Current beta
			BigInteger aBeta = beta;
			// Previous beta
			BigInteger pBeta = null;
		
			for (int i = 1; i <= r; i++) {
				// Beta^((p-1)/(q^i))
				BigInteger beta_pminus1byqexp = aBeta.modPow(
						order.divide(q.pow(i)), p);
				BigInteger x_ = null;
				// Exhastive check for all Beta(...) === g^((p-1)/q)^k ; 0 <= k
				// <= q-1
				BigInteger j = ZERO ;
				for (; j.compareTo(q) < 0; j = j.add(ONE)) {
					BigInteger leTry = g_pminus1byq.modPow(j, p);
					
					if (leTry.equals(beta_pminus1byqexp)) {
						x_ = j;
					//	System.out.println(" ");
						break;
						
					}
				}
				// Sanity check
				if (x_ == null)
					throw new Exception("Didn't find a matching exponenet.");
				else {
					equationXValues.add(x_);
					// Prepare for next x_
					pBeta = aBeta;
					// previousBeta * alpha^(-1)^(x_0)
					
					if(j.subtract(BigInteger.ONE).intValue()>=0)
					aBeta = pBeta.multiply(g.modInverse(p).modPow(x_.multiply(q.pow(i-1)), p).mod(p));
					
				}

			}
			BigInteger theX = ZERO;
			int idx = 0;
			for (BigInteger x : equationXValues) {
				theX = theX.add(x.multiply(q.pow(idx))).mod(q.pow(r));
				System.out.print("x" + idx + "=" + x + "\t");
				idx++;
			}
			System.out.println(" X=" + theX + " (mod " + q.pow(r) + ") ");
			finalX.add(theX);
			finalMod.add(q.pow(r));

		}
		BigInteger[] result = solveCRT(
				finalX.toArray(new BigInteger[finalX.size()]),
				finalMod.toArray(new BigInteger[finalMod.size()]));
		System.out.println("Discrete Log\t: x = " + result[0] + " (mod "
				+ result[1] + ")");
		BigInteger computedBeta = g.modPow(result[0], p);
		if (computedBeta.equals(beta)) {
			System.out.println("Asserted. You is teh winnar :)");
		} else {
			System.out.println("Assertion failed. :(");
			System.out.println("given beta\t= " + beta + "\nour beta\t="
					+ computedBeta);
		}
		;

	}

	private Map<Integer, Integer> primeFactors(BigInteger numbers)
			throws Exception {
		int factorsOf = numbers.intValue();
		int mult = 1;
		Map<Integer, Integer> factors = new HashMap<Integer, Integer>();
		for (Integer i = 2; i <= factorsOf / i; i++) {
			while (factorsOf % i == 0) {
				Integer currCount = factors.get(i);
				if (currCount == null) {
					factors.put(i, 1);
				} else {
					factors.put(i, currCount + 1);
				}
				mult *= i;
				factorsOf /= i;
			}
		}
		if (factorsOf > 1) {
			Integer currCount = factors.get(factorsOf);
			if (currCount == null) {
				factors.put(factorsOf, 1);
			} else {
				factors.put(factorsOf, currCount + 1);
			}
			mult *= factorsOf;
		}
		if (numbers.intValue() != mult)
			throw new Exception("Number  " + numbers + " is not a smooth prime");

		return factors;
	}

	// Obtained from
	// http://www.csee.umbc.edu/~stephens/crypto/CIPHERS/BigIntegerMath.java
	public static BigInteger[] solveCRT(BigInteger[] residue,
			BigInteger[] modulus) {
		if (residue.length != modulus.length)
			throw new IllegalArgumentException(
					"Residues and moduli are in different amounts.");

		for (int i = 0; i < modulus.length - 1; i++) {
			for (int j = i + 1; j < modulus.length; j++) {
				if (!(modulus[i].gcd(modulus[j]).equals(ONE)))
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
		if (m.compareTo(ZERO) <= 0)
			throw new IllegalArgumentException("Modulus must be positive.");
		BigInteger answer = b.mod(m);
		return (answer.compareTo(ZERO) < 0) ? answer.add(m) : answer;
	}
}
