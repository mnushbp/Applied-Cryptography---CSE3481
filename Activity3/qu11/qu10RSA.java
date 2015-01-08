package qu11;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;

import javax.crypto.Cipher;

public class qu10RSA {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		final BigInteger ONE = BigInteger.ONE;
		
		BigInteger n = new BigInteger(
				"94587468335128982981605019776781234618384857805657005686084562260910788622013722"
				+"07092649169084385369007124813013442783232496672858253283236322154223178706820376"
				+"30270674000828353944598575250177072847684118190067762114937353265007829546216602"
				+"56501187035611332577696332459049538105669711385995976912007767106063");

		BigInteger e = new BigInteger("74327");

		BigInteger p = new BigInteger(
				"10358344307803887695931304169230543785620607743682421994532795393937342395753127"
				+"888522373061586445417642355843316524942445924294144921649080401518286829171");

		BigInteger ct = new BigInteger("10870101966939556606443697147757930290262227730644958783498257036423105365610629"
				+"52991052582846432979261500260278236678653125327546335884041286783340625646715334"
				+"51395019521734099553221296896703454456327755743017818003765454489903326085581032"
				+"66831217073027652061091790342124418143422318965525239492387183438956");
		
		BigInteger q = n.divide(p); //n=pq 
		
		BigInteger phi = (q.subtract(ONE).multiply(p.subtract(ONE)));
		BigInteger d = e.modInverse(phi);

		KeyFactory	keyFactory =	KeyFactory.getInstance("RSA");
		
		RSAPrivateKeySpec privKeySpec= new RSAPrivateKeySpec(n, d);
		//RSAPrivateKeySpec pubKeySpec= new RSAPrivateKeySpec(e, d);
		
		RSAPrivateKey pri_key = (RSAPrivateKey)keyFactory.generatePrivate(privKeySpec); 
		//RSAPublicKey pub_key = (RSAPublicKey)keyFactory.generatePublic(pubKeySpec); 
	
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, pri_key);
		byte[] bk = cipher.doFinal(ct.toByteArray());
		
		System.out.println(new String(bk).trim());
	}

}
