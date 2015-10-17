package TP1Securite;

/**
 * This manipulates strings to transform them to bits
 */

public class BitsManager {
	
	public BitsManager() {}


	/**
	 * This method takes a string of characters as input,
	 * and transforms it into a string of corresponding bits
	 * @param  message the message of chars to transform
	 * @return         the message written in binary
	 */
	//Found at http://stackoverflow.com/questions/917163/convert-a-string-like-testing123-to-binary-in-java
	public String stringToBits(String message) {
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
		}
		return binaryMessage.toString();
	}

	
}
