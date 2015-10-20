package TP1Securite;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
	
	private static int[] generateRC4Key(int length) {
		Random rand = new Random();
		int[] key = new int[length];
		for (int i = 0; i < length; i++) {
			key[i] = rand.nextInt(2);
		}
		return key;
	}

	protected void start(int no_port) {
		ServerSocket ss;
   	 
	    /**
	     * Beginning of server code
	     */
	    System.out.println("Démarrage du serveur sur le port " + no_port);
	    System.out.println("(CTRL+C pour quitter)");
	    try {
	      /**
	       * Creation of socket server on port no_port
	       */
	      ss = new ServerSocket(no_port);
	    } 
	    catch (Exception e) {
	      System.out.println("Erreur a la creation du serverSocket: " + e);
	      return;
	    }
	    
	    
	    while(true) {
	    	
	      try {
	        /**
	         * Waiting for connexion
	         */
	    	System.out.println("En attente de connexion\n");
	        Socket sockClient = ss.accept();
	        System.out.println("Connexion entrante.");
	        /**
	         * IO Streams
	         */
	        BufferedReader in = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
	        PrintWriter out = new PrintWriter(sockClient.getOutputStream());
	       
	        String algorithm = in.readLine();
	        System.out.println("Algorithme reçu: " + algorithm + "\n");
			


	       	String message = in.readLine();
	       	System.out.println("Message reçu: " + message + "\n");

	        System.out.println("Encryption du message...\n");	        
	        
	        try 
	        {
	        	String s = "";
	        	String encryptedMessage = "";		
				
				/**
				 * Else if file is .txt file, apply algorithm
				 * and then add it to html template and send
				 * template back to browser
				 */
				if (algorithm.equals("Feistel")) {
					out.println("key");
					//TODO
				}
				else if (algorithm.equals("RC4")) {
					int[] RC4_key = generateRC4Key(32);
					//key to string
					String key = "";
					for (int i : RC4_key) {
						key += Integer.toString(i);
					}
					out.println(key);

					RC4 rc4 = new RC4(RC4_key);
					//RC4 rc4 = new RC4(RC4_key);
					encryptedMessage = rc4.encrypt(message);
				}
				else if (algorithm.equals("Hach")) {
					out.println("no key");
					Hach hach = new Hach();
					encryptedMessage = hach.hachMessage(message);
				}
				else if (algorithm.equals("MAC")) {
					MAC mac = new MAC();
					//generate key for encryption
					Random rand = new Random();
					int MAC_key = rand.nextInt(256); //get 8 bit key
					//key in string
					String string_key = "";
					string_key += Integer.toBinaryString(MAC_key);
					out.println(string_key);

					//encrypt
					encryptedMessage = mac.sign(message, MAC_key);
				}
				else if (algorithm.equals("CBC")) {
					
				}
				else {
					System.out.println("Mauvais algorithme! Choix possibles: [Feistel, RC4, Hach, MAC].\n");
				}
				System.out.println("Envoi du message chiffré...\n");
				out.println(encryptedMessage);

	
	        }
	        catch (Exception e)
	        {
	        	System.out.print(e.toString());
	        	out.println("500 internal server error");
	        }
	        
	        out.flush();
	        
	      } catch (Exception e) {
	        System.out.println("Error: " + e);
	      }
	    }
	}
	
	public static void main(String[] args) {
		int port_no = 0;
        if (args.length > 0) 
        {
            try {
                port_no = Integer.parseInt(args[0]);
                Server sw = new Server();
                sw.start(port_no);
            } 
            catch (NumberFormatException e) {
                System.err.println("Call example: Server 5000");
                System.exit(1);
            }
         }
       else {
            System.err.println("Call example: Server 5000");
        }

	}

}
