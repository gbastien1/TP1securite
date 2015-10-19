package TP1Securite;

import java.util.Random;
import java.util.ArrayList;

public class Feistel {
	ArrayList<String> TabKey = new ArrayList<String>();
	String ciphertext;

	public Feistel() {}

	private String F(String message, String key)
	{
		String result = "";
		for(int i = 0; i < 16; i++)
		{
			if(message.charAt(i) == key.charAt(i))
			{
				result = result + "1";
			}
			else
			{
				result = result + "0";
			}
		}
		return result;
	}

	private String keyGenerator() 
	{ //key fourni ?
		Random rand = new Random();
		String key;
		for(int i = 0; i < 16; i++)
		{
			int randomNumber = rand.nextInt(1) + 0;
			key += Integer.toString(randomNumber);
		}
		return key;
	}

	private String simpletour(String left, String right){ // Ajouter Key
		int counter = 0;
//		String key = keyGenerator();
//		TabKey.add(key);
		String temp = null;
		temp = F(right, key);
		temp = temp ^ left;
		return = right + temp;
	}

	public String encrypt(String message, String key, int nbtours){
		String bits = bitsManager.stringToBits(message);
		String[] plaintext = bitsManager.splitInChunks(bits, 32)
		String[] ciphertext = plaintext;

		for(int i = 0; i < plaintext.lenght();i++)
		{
			while(nbtours > 0)
			{
				ciphertext[i] = simpletour(ciphertext[i].substring(0,15), plaintext.substring(15));
				nbtours--;
			}
		}

		return ciphertext = ciphertext.substring(15) + ciphertext.substring(0,15);
	}





}