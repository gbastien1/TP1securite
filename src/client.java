package TP1Securite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Client {
	/** 
	 * @param no number associated to chosen algorithm by user
	 * @return String with algorithm name to apply to message
	 */
	private String getAlgorithm(int no) {
		String algo = "";
		switch (no) {
			case 1: algo = "Feistel"; break;
			case 2: algo = "RC4"; break;
			case 3: algo = "Hach"; break;
			case 4: algo = "MAC"; break;
			case 5: algo = "CBC";
		}
		return algo;
	}

	private static int[] generateRC4Key(int length) {
		Random rand = new Random();
		int[] key = new int[length];
		for (int i = 0; i < length; i++) {
			key[i] = rand.nextInt(1);
		}
		return key;
	}
	
	protected void start(int no_port) {
		Socket socket;
		BufferedReader in;
		PrintWriter out;
		
		System.out.println("Demarrage du client sur le port " + no_port);
		
		try {
	      /**
	       * Creation of socket on port port_no to localhost
	       */
	      socket = new Socket("localhost", no_port);
	      
	      /**
	       * InputStream and OutputStream creation for client-server communication
	       */
	      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	      out = new PrintWriter(socket.getOutputStream(), true);

	      /**
	       * To read user input from System.in
	       */
	      BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
			while (true) {	
	
				/**
				 * Evaluate user input and choose which code to run
				 *
				 * User chooses between the 5 algorithms, or no encryption at all, or exit
				 * Then he chooses between plain text and filename,
				 * then he writes the said text or filename
				 */
				BufferedReader br = null;
				String userInput = "";
				String algorithm = "";
				//choose algorithm
			do {
				System.out.println("Please enter corresponding number from list:\n"
						+ "1- Feistel\n"
						+ "2- RC4\n"
						+ "3- Hach\n"
						+ "4- MAC\n"
						+ "5- CBC");
				userInput = userInputBR.readLine();
				if (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 5) {
					System.out.println("Please choose between 1 and 5.\n");
				}
				algorithm = getAlgorithm(Integer.parseInt(userInput));
			}
			while (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 5);

			//choose between plain text from console or text from file
			String message = "";
			do
			{
				System.out.println("Please enter corresponding number from list:\n"
						+ "1- plain text\n"
						+ "2- text from file\n");
				userInput = userInputBR.readLine();
				if (userInput.equals("1")) 
				{
					System.out.println("Please type in text directly and press enter: \n");
					message = userInputBR.readLine();
				}
				else if (userInput.equals("2"))
				{
					System.out.println("Please type in filename (do not forget its extension):\n");
					String filename = userInputBR.readLine();
					/**
					 * Read file with given filename
					 */
					br = new BufferedReader(new FileReader("../data/" + filename));
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
				
				int originalMessageLength = message.length()*8; //8 bit per character
				/**
				 * Send message to server
				 */
				//Send algorithm and message in clear
				out.println(algorithm);
				out.println(message);

				//apply algorithm to message
				String key = in.readLine();
				String encryptedMessage = in.readLine();
				System.out.println("Message chiffre recu du serveur: " + encryptedMessage);

				if (algorithm.equals("Feistel")) {
					//TODO
				}
				else if (algorithm.equals("RC4")) {
					//transform String key to int []
					int[] RC4_key = new int[32];
					for (int i = 0; i < key.length(); i++) {
						RC4_key[i] = (int) key.charAt(i) - 48;
					}
					RC4 rc4 = new RC4(RC4_key);
					message = rc4.decrypt(encryptedMessage);
				}
				else if (algorithm.equals("Hach")) {
					message = "Impossible d'effectuer le hachage en sens inverse.";
				}
				else if (algorithm.equals("MAC")) {
					//generate key for encryption
					//transform String key to int
					int MAC_key = Integer.parseInt(key, 2); //get 8 bit key
					MAC mac = new MAC();
					boolean identical = mac.compare(encryptedMessage, MAC_key, originalMessageLength);

					if(identical) {
						message = "La comparaison a fonctionne! C'est bel et bien server qui a envoye le message!";
					}
					else {
						message = "La comparaison a echoue! le message a ete modifie!";
					}
				}
				else if (algorithm.equals("CBC")) {

				}
				else {
					System.out.println("Mauvais algorithme! Choix possibles: [Feistel, RC4, Hach, MAC].\n");
				}

				//send decrypted message to server
				System.out.println("Message dechiffre: " + message);
				
				out.flush();
				
			}	
		
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	    catch (Exception e) {
	      System.out.println("Erreur a la creation du socket: " + e);
	      return;
	    }
		
	}
	
	public static void main(String[] args) {
		int port_no = 0;
        if (args.length > 0) 
        {
            try {
                port_no = Integer.parseInt(args[0]);
                Client sw = new Client();
                sw.start(port_no);
            } 
            catch (NumberFormatException e) {
                System.err.println("Call example: Client 5000");
                System.exit(1);
            }
         }
       else {
            System.err.println("Call example: Client 5000");
        }

	}

}
