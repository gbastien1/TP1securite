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

	private String simpletour(String left, String right, String key){ // Ajouter Key
		int counter = 0;
		String temp = null;
		temp = F(right, key);
		int x = Integer.parseInt(temp,2) ^ Integer.parseInt(left,2);
		temp = Integer.toBinaryString(x);
		return right + temp;
	}

	public String encrypt(String message, String key, int nbtours){
		BitsManager bitsManager = new BitsManager();
		String bits = bitsManager.stringToBits(message);
		String[] plaintext = bitsManager.splitInChunks(bits, 32);
		String[] ciphertext = plaintext;
		String keyTemp = "";
		String encryptedMessage = "";

		for(int i = 0; i < plaintext.length;i++)
		{
			while(nbtours > 0)
			{
				ciphertext[i] = simpletour(ciphertext[i].substring(0,16), ciphertext[i].substring(16), key);
				keyTemp = Character.toString(key.charAt(0));
				key = key.substring(1,15);
				key += keyTemp;
				nbtours--;
			}
			ciphertext[i] = ciphertext[i].substring(16) + ciphertext[i].substring(0,16);
		}

		for(int i = 0; i < ciphertext.length; i++)
		{
			encryptedMessage += ciphertext[i];
		}
		return encryptedMessage;
	}

	public String decrypt(String message, String key, int nbtours){
		BitsManager bitsManager = new BitsManager();
		String bits = bitsManager.stringToBits(message);
		String[] plaintext = bitsManager.splitInChunks(bits, 32);
		String[] ciphertext = plaintext;
		String keyTemp = "";
		String decryptedMessage = "";

		for(int i = 0; i < plaintext.length;i++)
		{
			while(nbtours > 0)
			{
				ciphertext[i] = simpletour(ciphertext[i].substring(0,16), ciphertext[i].substring(16), key);
				keyTemp = Character.toString(key.charAt(15));
				key = key.substring(0,15);
				key = keyTemp + key;
				nbtours--;
			}
			ciphertext[i] = ciphertext[i].substring(16) + ciphertext[i].substring(0,16);
		}

		for(int i = 0; i < ciphertext.length; i++)
		{
			decryptedMessage += ciphertext[i];
		}
		return decryptedMessage;
	}





}