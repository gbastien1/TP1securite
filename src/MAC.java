package TP1Securite;

/**
 * This uses the HMAC algorithm
 * **the secret key is not securely transfered, 
 * **but this should be implemented in real practice
 *
 * steps:
 * -XOR key with ipad (00110110) -> Si
 * -append message to Si -> donnee
 * -Hach donnee -> hash
 * -XOR key with opad (01011100) -> S0
 * -append hash to S0 -> hashdonnee
 * -hach hashdonnee
 */

public class MAC {
	
	public MAC() {}

	/**
	 * This method applies a simple HMAC signature function 
	 * to a message using a "secret" key
	 * @param  message the message to sign
	 * @param  key     the "secret" key used in HMAC in binary
	 * @return         the signed message in binary
	 */
	public String sign(String message, String key) {
		BitsManager bitsManager = new BitsManager();
		Hach hashFct = new Hach();
		String ipad = "00110110";
		String opad = "01011100";
		int ipad_int = Integer.parseInt(ipad, 2); //returns int value of binary string
		int opad_int = Integer.parseInt(opad, 2);
		int key_int = Integer.parseInt(key, 2);
		String messageBits = bitsManager.stringToBits(message); //to transform a string to its bits string equivalent

		//XOR between ipad and key
		int xor_key_ipad = ipad_int ^ key_int;
		String xor_key_ipad_binary = Integer.toBinaryString(xor_key_ipad);
		//append message to previous XOR
		xor_key_ipad_binary += messageBits;
		//hash previous XOR+message
		String hashed_ipad = hashFct.hachMessage(xor_key_ipad_binary);

		//same with opad
		int xor_key_opad = opad_int ^ key_int;
		String xor_key_opad_binary = Integer.toBinaryString(xor_key_opad);
		xor_key_opad_binary += hashed_ipad;

		return hashFct.hachMessage(xor_key_opad_binary);
	}
	

	public String compare(String encryptedMessage, String hashcode) {
		
		return null;
	}
	
}
