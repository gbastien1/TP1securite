package TP1Securite;

import java.util.Random;
import java.util.ArrayList;

//Found at http://www.commentcamarche.net/forum/affich-25066859-cryptography-algorithme-de-feistel-en-java-c

public class Feistel {
	ArrayList<String> TabKey = new ArrayList<String>();
	String ciphertext;

	public Feistel() {}

	private String pseudo(char block, String key){	      //Pseudo-aléatoire
		int intBlock = Integer.parseInt(block);
		int intKey = Integer.parseInt(key);

		intBlock = (intBlock * 7 + 3) % 256;
		intBlock = intBlock & intKey;
		return Integer.toBinaryString(intBlock);
	}

	private String keyGenerator()
	{
		Random rand = new Random();
		String key;
		for(int i = 0; i < 16; i++)
		{
			int randomNumber = rand.nextInt(1) + 0;
			key += Integer.toString(randomNumber);
		}
		return key;
	}

	private void simpletour(String plaintext){ //À refaire!
		int counter = 0;
		String key = keyGenerator();
		TabKey.add(key);

		while(counter < plaintext.length()){
			ciphertext.charAt(2*counter) = plaintext.charAt(2*counter+1);
			ciphertext.charAt(2*counter+1) = plaintext.charAt(2*counter) ^ pseudo(plaintext.charAt(2*counter+1), key);	
			counter++;
		}
	}

	public String encrypt(String message, int nbtours){
		String plaintext = bitsManager.stringToBits(message);
		ciphertext = plaintext;

		String temp;
		while(nbtours > 0){
			simpletour(plaintext);
			temp = plaintext;
			plaintext = ciphertext;
			ciphertext = temp;
			nbtours--;
		}
		return ciphertext;
	}

	public String decrypt(String ciphertext, int nbtours){
		String temp;
		while(nbtours > 0){
			simpletour(ciphertext);
			temp = ciphertext;
			ciphertext = plaintext;
			ciphertext = temp;
			nbtours--;
		}
		return plaintext;
	}
}