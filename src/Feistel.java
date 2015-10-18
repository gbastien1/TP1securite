package TP1Securite;

//http://www.commentcamarche.net/forum/affich-25066859-cryptography-algorithme-de-feistel-en-java-c

public class Feistel {

	public Feistel(String message) {

		String plaintext = bitsManager.stringToBits(message); 
        
        String ciphertext = new String[plaintext.length];	
	}

	char pseudo(String caractere){	      //Pseudo-aléatoire 
		return (String) ( 7*((int) caractere ) + 3 ) % 256;
	}

	public simpletour(String plaintext){
		int counter = 0;

		while(counter < plaintext.length){
			ciphertext[2*counter] = plaintext[2*counter+1];
			ciphertext[2*counter+1] = plaintext[2*counter] ^ pseudo(plaintext[2*counter+1]);	
			counter++;
		}
	}

	public String encrypt(String plaintext, int nbtours){
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

}