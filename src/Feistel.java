package TP1Securite;

//http://www.commentcamarche.net/forum/affich-25066859-cryptography-algorithme-de-feistel-en-java-c

public class Feistel {
	ArrayList<String> TabKey = new ArrayList<String>();

	public Feistel(String message) {
		String plaintext = bitsManager.stringToBits(message); 
        
        String ciphertext = new String[plaintext.length];	
	}

	private String pseudo(String block, String key){	      //Pseudo-aléatoire 
		int intBlock = Integer.parseInt(block);
		int intKey = Integer.parseInt(key);

		intBlock = (intBlock * 7 + 3) % 256;
		intBlock = intBlock & intKey;
		return Integer.toBinaryString(intBlock);
	}

	public String keyGenerator()
	{
		String key;
		for(int i = 0; i < 16; i++)
		{
			int randomNumber = random.nextInt(1) + 0;
			key += Integer.toString(randomNumber);
		}
		return key;
	}


	public simpletour(String plaintext){
		int counter = 0;
		String key = keyGenerator();
		TabKey.add(key);

		while(counter < plaintext.length){
			ciphertext[2*counter] = plaintext[2*counter+1];
			ciphertext[2*counter+1] = plaintext[2*counter] ^ pseudo(plaintext[2*counter+1], key);	
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