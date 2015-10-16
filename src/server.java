
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
	
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
	       
	       	String message = in.readLine();
	       	System.out.println("Message reçu: " + message + "\n");

	        //System.out.println("Envoi du contenu...");	        
	        
	        try 
	        {
	        	String algorithm = "";
	        	String encryptedMessage = "";
				String s = "";
								
				encryptedMessage = in.readLine();
				if (encryptedMessage != null) {
					out.println("OK");
				}
				
				/**
				 * Else if file is .txt file, apply algorithm
				 * and then add it to html template and send
				 * template back to browser
				 */
				if(algorithm != null) { //this is a text file
					
					/**
					 * Then, look at path to see which algorithm to decrypt or compare,
					 */
					/*if (algorithm.equals("RC4")) 
					{
						RC4 rc4 = new RC4(encryptedMessage);
						//rc4.decrypt(encryptedMessage);
						//phil
						
					}
					else if (algorithm.equals("CBC")) 
					{
						CBC cbc = new CBC(encryptedMessage);
						//?
					}
					else if (algorithm.equals("Feistel")) 
					{
						Feistel feistel = new Feistel(encryptedMessage);
						//phil
					}
					else if (algorithm.equals("Authentication")) 
					{
						Authentication auth = new Authentication(encryptedMessage);
						//moi
					}
					else if (algorithm.equals("Hach")) 
					{
						Hach hach = new Hach(encryptedMessage);
						//moi
					}*/
				}
				else {
					/**
					 * do not need to decrypt or compare from any algorithm
					 */
				}
				
	        }
	        catch (Exception e)
	        {
	        	System.out.print(e.toString());
	        	out.println("500 internal server error");
	        }
	        
	        out.flush();
		    sockClient.close();
	        
	      } catch (Exception e) {
	        System.out.println("Error: " + e);
	      }
	    }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
