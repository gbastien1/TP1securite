package TP1Securite;

import java.util.ArrayList;

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


	public String bitsToString(String bitsString) {
		//Split every nth character found here: 
		//http://stackoverflow.com/questions/12295711/split-a-string-at-every-nth-position
		int count = 8;
		String[] bitsbytes = bitsString.split("(?<=\\G.{" + count + "})");

		String messageInChars = "";
		for (String b : bitsbytes) {
			int val = Integer.parseInt(b, 2);
			messageInChars += Character.toChars(val)[0];
		}

		return messageInChars;
	}

	/**
	 * This method checks if number is a factor of factor
	 * @param  factor the number to divide by
	 * @param  number the number to compare its factors
	 * @return   true if number is factor of factor, false otherwise
	 */
	//Found at http://www.exploringbinary.com/ten-ways-to-check-if-an-integer-is-a-power-of-two-in-c/
	private boolean isFactorOf(int factor, int number)
	{
	 	return number % factor == 0;
	}

	public String[] splitInChunks(String bits, int chunkSize) {
		ArrayList<String> chunks = new ArrayList<String>();

		//pad the bits string
		int index = 0;
		while (!isFactorOf(chunkSize, bits.length())) {
			if (index == 0) {
				bits += '1';
			}
			else {
				bits += '0';
			}
			index++;
		}
		
		//divide bits in chunks of chunkSize bits
		int numberOfChunks = bits.length() / chunkSize;
		for (int n = 0; n < numberOfChunks; n++)
		{
			String chunk = "";
			for (int i = 0; i < chunkSize; i++)
			{
				chunk += bits.charAt(i + n * chunkSize);
			}
			chunks.add(chunk);
		}
		String[] chunksString = new String[chunks.size()];
		return chunks.toArray(chunksString);
	}

	
}
