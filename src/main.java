package TP1Securite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class main {

	/** 
	 * @param no number associated to chosen algorithm by user
	 * @return String with algorithm name to apply to message
	 */
	private static String getAlgorithm(int no) {
		String algo = "";
		switch (no) {
			case 1: algo = "Feistel"; break;
			case 2: algo = "RC4"; break;
			case 3: algo = "Hach"; break;
			case 4: algo = "MAC"; break;
		}
		return algo;
	}

	/**
	 * Acting as the server, function that encrypts/hashes the message
	 * @param message   The message as String to encrypt/hash
	 * @param algorithm The algorithm to use
	 */
	public static String server(String message, String algorithm) {
		String encryptedMessage = "";

		if (algorithm.equals("Feistel")) {

		}
		else if (algorithm.equals("RC4")) {

		}
		else if (algorithm.equals("Hach")) {

		}
		else if (algorithm.equals("MAC")) {

		}
		else {
			System.out.println("Mauvais algorithme! Choix possibles: [Feistel, RC4, Hach, MAC].\n");
		}

		return encryptedMessage;
	}

	/**
	 * Acting as the client, this function decrypts/compares the hash of the message returned by the client
	 * @param encryptedMessage the message to decrypt / compare hash
	 * @param algorithm        [The algorithm that was used by the server
	 */
	public static String client(String encryptedMessage, String algorithm) {
		String message = "";
		if (algorithm.equals("Feistel")) {

		}
		else if (algorithm.equals("RC4")) {

		}
		else if (algorithm.equals("Hach")) {

		}
		else if (algorithm.equals("MAC")) {

		}
		else {
			System.out.println("Mauvais algorithme! Choix possibles: [Feistel, RC4, Hach, MAC].\n");
		}

		return message;
	}

	/**
	 * La fonction main est une boucle dans laquelle on choisit l'algorithme à appliquer,
	 * on écrit le message ou on lit le fichier qui contient le message, puis on applique
	 * l'algorithme de chiffrement sur le serveur, et le client déchiffre / compare etc.
	 * @param args[] aucun paramètre en ligne de commande
	 */
	public static void main(String args[]) {
		try {

			BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader br = null;
			String userInput = "";
			String algorithm = "";
			//choose algorithm
			do {
				System.out.println("Please enter corresponding number from list:\n"
						+ "1- Feistel\n"
						+ "2- RC4\n"
						+ "3- Hach\n"
						+ "4- MAC\n");
				userInput = userInputBR.readLine();
				if (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 4) {
					System.out.println("Please choose between 1 and 4.\n");
				}
				algorithm = getAlgorithm(Integer.parseInt(userInput));
			}
			while (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 4);

			//choose between plain text from console or text from file
			String message = "";
			do
			{

				System.out.println("Please enter corresponding number from list:\n"
						+ "1- plain text\n"
						+ "2- text from file\n");
				userInput = userInputBR.readLine();
				if (userInput.equals("1")) {
					System.out.println("Please type in text directly and press enter: \n");
					message = userInputBR.readLine();
				}
				else if (userInput.equals("2")) {
					System.out.println("Please type in filename (do not forget its extension):\n");
					userInput = userInputBR.readLine();
					/**
					 * Read file with given filename
					 */
					br = new BufferedReader(new FileReader(userInput));
					message = "";
					String s;
					while((s = br.readLine()) != null) {
						message += s;
					}
				}
				else {
					System.out.println("Please enter 1 or 2.\n");
				}
			}
			while (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 2);

			System.out.println("Algorithm is " + algorithm + '\n');
			System.out.println("Message in clear is " + message + '\n');
			String encryptedMessage = server(message, algorithm);
			System.out.println("Server encrypted message. Result is " + encryptedMessage + '\n');
			System.out.println("Client decrypted message. Result is " + client(encryptedMessage, algorithm) + '\n');
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}