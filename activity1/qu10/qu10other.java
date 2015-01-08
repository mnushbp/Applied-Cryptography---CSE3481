package qu10;

import util.ByteWorks;
import util.LetterWorks;

public class qu10other {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "./CT.txt";
		//String path = ClassLoader.getSystemResource(fileName+".txt").getPath();
		final int E_INDEX_ACTUAL = 4;
		try {
		byte[] cipherText = ByteWorks.fileToBytes(fileName);
		int keyLength = determineKeyLength(cipherText);
		if(keyLength < 0) {
		System.out.println("Could not find key length, operation stops.\n");
		} else {
		System.out.printf("Found key of length %d, proceeding with decryption.\n", keyLength);
		byte[] allSimilarlyShifted = new byte[cipherText.length/keyLength];
		int freqs[];
		int shift;
		int shifts[] = new int[keyLength];
		int currentIndex = 0;
		int estimatedEIndex = 0;
		while(currentIndex < keyLength) {
		//put each character at the current index of the key in an array
		for(int i = 0; i < allSimilarlyShifted.length; i++)	
		allSimilarlyShifted[i] = cipherText[currentIndex+i*keyLength];
		freqs = LetterWorks.getFrequencies(allSimilarlyShifted);
		int highestFreq = 0;
		//determine letter with highest frequency - this will be E in plaintext
		for(int i = 0; i < freqs.length; i++) {
		if(freqs[i] > highestFreq)
		{
		highestFreq = freqs[i];
		estimatedEIndex = i;
		}
		}
		//put shift amount in key array
		shift = estimatedEIndex - E_INDEX_ACTUAL;
		if(shift < 0)
		shift = 25 + shift;
		shifts[currentIndex] = shift;
		currentIndex++;
		}
		//print key array - this will be the key
		System.out.print("Shifts: ");
		for(int i : shifts){
		System.out.printf(" %c :", i);
		}
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		private static int determineKeyLength(byte[] ar) {
		int keyLength = 1;
		int cipherIndex = 0;
		double indexOfCoincidence = 0;
		byte[] comparison;
		int arraySize;
		//try different key lengths until a proper IoC is found
		while (keyLength != ar.length) {
		arraySize = ar.length / keyLength;
		comparison = new byte[arraySize];
		//put each letter at the requisite interval in an array
		for(int i = 0; i < comparison.length; i++) {;
		comparison[i] = ar[cipherIndex];
		cipherIndex += keyLength;
		}
		indexOfCoincidence = LetterWorks.getFreqIC(comparison);
		System.out.printf("KeyLength %d produced IoC %f\n", keyLength, indexOfCoincidence);
		//if IoC approaches 7 (english language IoC)
		if(indexOfCoincidence > 0.06) {
		return keyLength;
		}
		cipherIndex = 0;
		keyLength++;	
		}
		//if no key found
		return -1;
		}
}
