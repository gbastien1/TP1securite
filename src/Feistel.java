package TP1Securite;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class Feistel {

	public Feistel() {}

	/**
	 * la fonction de brouillage F qui effectue un ET binaire 
	 * entre la clé et un bloc du message
	 * @param  message Un bloc de 16 bits
	 * @param  key     une clé de 16 bits
	 * @return         résultat de ET binaire
	 */
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

	/**
	 * Fonction de tour de Feistel, qui effectue un brouillage avec F et un XOR
	 * avec un bloc de 16 bits du bloc en cours de chiffrement
	 * @param  left  bloc de 16 bits de gauche
	 * @param  right bloc de 16 bits de droite
	 * @param  key   clé de 16 bits
	 * @return       concaténation du bloc de droite et du résultat du brouillage + XOR
	 */
	private String simpletour(String left, String right, String key){ // Ajouter Key
		String temp = null;
		temp = F(right, key);
		int x = Integer.parseInt(temp,2) ^ Integer.parseInt(left,2);
		//entier en String en "paddant" pour 16 bits
		temp = String.format("%16s", Integer.toBinaryString(x)).replace(' ', '0');		

		return right + temp;
	}

	/**
	 * Chiffrement Feistel, donc un nombre n (16) de "simpletour", en utilisant une 
	 * clé dérivée de la première clé à chaque tour (shift à droite)
	 * @param  message message à chiffrer
	 * @param  key     clé de 16 bits
	 * @param  nbtours nombre de tours de Feistel
	 * @return         message encrypté en binaire
	 */
	public String encrypt(String message, String key, int nbtours){
		BitsManager bitsManager = new BitsManager();
		String bits = bitsManager.stringToBits(message);
		String[] plaintext = bitsManager.splitInChunks(bits, 32);
		String[] ciphertext = Arrays.copyOf(plaintext, plaintext.length);
		String keyTemp = "";
		String encryptedMessage = "";

		for(int i = 0; i < plaintext.length;i++)
		{
			while(nbtours > 0)
			{
				ciphertext[i] = simpletour(ciphertext[i].substring(0,16), ciphertext[i].substring(16), key);
				//shift droit des bits de la clé à chaque tour
				keyTemp = Character.toString(key.charAt(0));
				key = key.substring(1,16);
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


	/**
	 * Déchiffrement Feistel, donc un nombre n (16) de "simpletour", en utilisant une 
	 * clé dérivée de la première clé à chaque tour (shift à gauche)
	 * @param  message message à déchiffrer en binaire
	 * @param  key     clé de 16 bits
	 * @param  nbtours nombre de tours de Feistel
	 * @return         message encrypté en caractères
	 */
	public String decrypt(String bits, String key, int nbtours){
		BitsManager bitsManager = new BitsManager();
		String[] plaintext = bitsManager.splitInChunks(bits, 32);
		String[] ciphertext = Arrays.copyOf(plaintext, plaintext.length);
		String keyTemp = "";
		String decryptedMessage = "";

		for(int i = 0; i < plaintext.length;i++)
		{
			while(nbtours > 0)
			{
				ciphertext[i] = simpletour(ciphertext[i].substring(0,16), ciphertext[i].substring(16), key);
				//shift gauche des bits de la clé à chaque tour
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
		return bitsManager.bitsToString(decryptedMessage);
	}





}