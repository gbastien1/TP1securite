package TP1Securite;

/**
 * This hash function uses SHA-512 algorithm
 *
 * steps:
 * 1- split message of n bits into b blocks (n should be a factor of b)
 * 2- XOR each block with each other
 * 3- obtain hash code
 *
 * 1010001101001
 * 0101010010010
 * 0010100110010
 * 0100100101100
 * _____________
 * 10...........
 * 
 */

public class Hach {

	public Hach(String filecontent) {
		stringToBits(filecontent);
	}

	//Found at http://stackoverflow.com/questions/917163/convert-a-string-like-testing123-to-binary-in-java
	private String stringToBits(String message) {
		byte[] bytes = message.getBytes();
		StringBuilder binaryMessage = new StringBuilder();
		
		//transform bytes to bits
		for (byte b : bytes)
		{
			int val = b;
			for (int i = 0; i < 8; i++)
			{
				/**
				 * what this does:
				 * 	-it compares the 8th bit of val with 0 using a '1000000' bit mask. if it is 0, append 0, else append 1.
				 * 	-it then shifts the bits one position to the left and compares again for the 7 other bits of the byte
				 */
				binaryMessage.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
			//binaryMessage.append(' '); //no need for spaces, only for debug purpose
		}
		System.out.println("'" + message + "' to binary: " + binaryMessage);
		return binaryMessage.toString();
	}

	public String hashMessage(String message) {
		return null;
	}
	
}
