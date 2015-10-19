package TP1Securite;

import java.util.ArrayList;

/**
 * This hash function is the simplest: xor on chunks of bits of fixed size
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

	public Hach() {	
	}

	/**
	 * This method applies a xor to a number of integers
	 * and returns a binary representation of the result
	 * @param  intChunks chunks of bits as integers to apply XOR on
	 * @return           the result of XOR as binary string
	 */
	private String xor(long[] intChunks) {
		if (intChunks.length < 2) {
			return Long.toBinaryString(intChunks[0]);
		}
		int index = 0;
		long newChunk;
		newChunk = intChunks[0] ^ intChunks[1];
		for (int i = 2; i < intChunks.length; i++)
		{
			newChunk = newChunk ^ intChunks[i]; //^ is Java's xor operator on integers
		}
		
		return Long.toBinaryString(newChunk);
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

	
	

	/**
	 * This is the actual simple hach function. It takes a message in parameters,
	 * transforms it into a string of bits, pad the string with '10000...' bits to 
	 * make it a factor of 64 bits, then separates it into chunks of 64 bits, and
	 * applies a XOR on each nth bit of each chunk to produce a 64 bits string result.
	 * @param  message the message to hach, in chars
	 * @return         the hash code corresponding to the message
	 */
	public String hachMessage(String message) {
		BitsManager bitsManager = new BitsManager();
		int chunkSize = 64;
		String hashcode = "";

		//get corresponding bits
		String bits = bitsManager.stringToBits(message);
		
		//split bits in chunks of chunkSize bits
		String[] chunks = bitsManager.splitInChunks(bits, chunkSize);

		//XOR on each chunk to produce hash code
		//first: get the chunks and get integers from them
		long[] integerChunks = new long[chunks.length];
		
		for (int i = 0; i < chunks.length; i++) 
		{
			long val = Long.parseLong(chunks[i], 2); //gets long from binary string
			integerChunks[i] = val;
		}
		
		//then: xor all the chunks to form a hash code
		hashcode = xor(integerChunks);

		return hashcode;
	}
	
}
