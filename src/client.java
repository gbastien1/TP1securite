
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class client {
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
			case 4: algo = "Authentication"; break;
			case 5: algo = "CBC"; break;
			case 6: algo = null;
		}
		return algo;
	}
	
	protected void start(int no_port) {
		Socket socket;
		BufferedReader in;
		PrintWriter out;
		
		System.out.println("Démarrage du client sur le port " + no_port);
		
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
				
				System.out.println("Reçu du serveur:" + in.readLine());
	
	
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
				do {
					System.out.println("Please enter corresponding number from list:\n"
							+ "1- Feistel\n"
							+ "2- RC4\n"
							+ "3- Hach\n"
							+ "4- Authentication\n"
							+ "5- CBC\n"
							+ "6- send raw message\n"
							+ "7- exit\n");
					userInput = userInputBR.readLine();
					if (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 7) {
						System.out.println("Please choose between 1 and 7.\n");
					}
					else if (Integer.parseInt(userInput) == 7) {
						System.out.println("Reçu du serveur:" + in.readLine());
						socket.close();
						break;
					}
					algorithm = getAlgorithm(Integer.parseInt(userInput));
				}
				while (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 7);
				do
				{
					System.out.println("Please enter corresponding number from list:\n"
							+ "1- plain text\n"
							+ "2- text from file\n");
					userInput = userInputBR.readLine();
					if (userInput == "1") {
						System.out.println("Please type in text directly and press enter: \n");
						userInput = userInputBR.readLine();
					}
					else if (userInput == "2") {
						System.out.println("Please type in filename (do not forget its extension):\n");
						userInput = userInputBR.readLine();
						/**
						 * Read file with given filename
						 */
						br = new BufferedReader(new FileReader(userInput));
						userInput = "";
						String s;
						while((s = br.readLine()) != null) {
							userInput += s;
						}
					}
					else {
						System.out.println("Please enter 1 or 2.\n");
					}
				}
				while (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > 2);
				
				
				/**
				 * Send message to server
				 */
				//apply algorithm to message
				//TODO
				String encryptedMessage = "";
				//TODO
				
				//Send algorithm and wait for response
				out.println(algorithm);
				String response = in.readLine();
				if (response.equals("OK")) {
					//send message and wait for response
					out.println(encryptedMessage);
					response = in.readLine();
				}
	
				System.out.println("Reçu du serveur:" + in.readLine());
	
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
		// TODO Auto-generated method stub

	}

}
