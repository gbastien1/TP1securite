import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;


/**
 * Ceci est un simple serveur web dont la structure de base
 * fut implémentée dans un cours de réseau passé. On se sert 
 * de ce serveur web et du navigateur comme client.
 * 
 * @fichiers Les fichiers à lire doivent être dans le répertoire
 * data à la racine du projet
 * 
 * @commandLine Ligne de commande pour lancer le serveur:
 * > java -cp . WebServer no_port
 * 
 * @Utilisation1 Dans le navigateur, il suffit d'entrer l'url
 * localhost:xxxx/algorithme/fichier.txt
 * ce qui a pour effet d'envoyer une requête au serveur via localhost
 * sur le port xxxx pour appliquer l'algorithme spécifié sur le
 * fichier donné.
 * 
 * @utilisation2 Un url du type localhost:xxxx/fichier.html
 * fera une requête normale sur un fichier html. Le serveur 
 * prend en compte les fichiers CSS inclus dans le HTML.
 * 
 * @output après une requête d'un fichier, le serveur répond 
 * une page html, que ce soit celle demandée par /fichier.html,
 * ou un template html contenant la sortie de l'algorithme
 */

public class WebServer {

	private static Pattern pathRegex = Pattern.compile("^/[a-zA-Z0-9_]*.?[a-zA-Z0-9]*/?[a-zA-Z0-9_]*.?[a-zA-Z0-9]*$");
	private static Pattern httpRegex = Pattern.compile("^(HTTP|http)/(0.9|1.0|1.1)$");
	boolean pathOk = false;
	boolean httpOk = false;
	
	String httpVersion = "";
	
	protected String createResponseString(String httpVersion, String contentType) {
		String output = "";
		output += httpVersion + " 200 OK\n";
		output += "Content-Type: " + contentType + "\n";
		output += "Server: localhost\n";
		return output;
	}
	
	protected void start(int port_no) {
	    ServerSocket ss;
	    	 
	    /**
	     * Beginning of server code
	     */
	    System.out.println("Démarrage du serveur web sur le port " + port_no);
	    System.out.println("(CTRL+C pour quitter)");
	    try {
	      /**
	       * Creation of socket server on port port_no
	       */
	      ss = new ServerSocket(port_no);
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
	         * IO for text / html / css files
	         */
	        BufferedReader in = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
	        PrintWriter out = new PrintWriter(sockClient.getOutputStream());
	        

	        System.out.println("Envoi du contenu...");	        
	        
	        try 
	        {
		        		        	
		        
			        	String algorithm = "";
			        	String filename = "";
						String path = arrayReq[1];
						String s = "";
										
						if (path.equals("/")) path = "index.html"; // request to / and to /index.html are the same
						else path = path.substring(1); //forget last '/'
						
						/**
						 * Retrieve algorithm and filename from request path
						 */
						String [] pathParts = path.split("/"); //get algo and file separately
						if (pathParts.length > 1) {
							algorithm = pathParts[0];
							filename = pathParts[1];
						}
						else {
							filename = pathParts[0];
						}
						
						/**
						 * Retrieve file extension from request path
						 */
						String extension = path.substring(path.indexOf(".") + 1); //extraire l'extension du fichier
						extension = extension.toUpperCase();
						
						/**
						 * folder named data containing the files
						 */
						String dataFolder = "../data/";
						
						BufferedReader br = null;
						/**
						 * Bug fix for favicon.ico bug, send simple OK response
						 */
						if (extension.equals("ICO")) { 
							out.println(httpVersion + " 200 OK");
						}
						/**
						 * If file is simple html file, send it to browser
						 */
						else if (extension.equals("HTML")) {						
							/**
							 * Send OK response
							 */
							out.println(createResponseString(httpVersion, "text/html"));
							
							/**
							 * Send HTML file to browser
							 */
							br = new BufferedReader(new FileReader(dataFolder + filename));
							while((s = br.readLine()) != null) {
								out.println(s);
							}
						}
						/**
						 * If HTML requests CSS, send it as text/css response
						 */
						else if (extension.equals("CSS")) {						
							/**
							 * Send OK response
							 */
							out.println(createResponseString(httpVersion, "text/css"));
							
							/**
							 * Send CSS file to browser
							 */
							br = new BufferedReader(new FileReader(dataFolder + filename));
							while((s = br.readLine()) != null) {
								out.println(s);
							}
						}
						/**
						 * Else if file is .txt file, apply algorithm
						 * and then add it to html template and send
						 * template back to browser
						 */
						else if(extension.equals("TXT")) { //this is a text file
							
							/**
							 * Send OK response
							 */
							out.println(createResponseString(httpVersion, "text/html"));
							
							/**
							 * Get html templates
							 */
							String upperTemplate = "";
							String lowerTemplate = "";
							BufferedReader up_br = new BufferedReader(new FileReader("templateUp.txt"));
							while((s = up_br.readLine()) != null) {
								upperTemplate += s;
							}
							BufferedReader low_br = new BufferedReader(new FileReader("templateDown.txt"));
							while((s = low_br.readLine()) != null) {
								lowerTemplate += s;
							}
							
							/**
							 * Now, read file content and place it in a String
							 */
							br = new BufferedReader(new FileReader(dataFolder + filename));
							String filecontent = "";
							while((s = br.readLine()) != null) {
								filecontent += s;
							}
							
							/**
							 * Then, look at path to see which algorithm to apply to file,
							 * and execute algorithm to send new data to browser afterwards
							 */
							if (algorithm.equals("RC4")) 
							{
								RC4 rc4 = new RC4(filecontent);
								
								out.println(upperTemplate);
								out.println(filecontent); //THIS IS WHERE THE OUTPUT GOES
								out.println(lowerTemplate);
							}
							else if (algorithm.equals("CBC")) 
							{
								CBC cbc = new CBC(filecontent);
							}
							else if (algorithm.equals("Feistel")) 
							{
								Feistel feistel = new Feistel(filecontent);
							}
							else if (algorithm.equals("Authentication")) 
							{
								Authentication auth = new Authentication(filecontent);
							}
							else if (algorithm.equals("Hach")) 
							{
								Hach hach = new Hach(filecontent);
							}
							else {
								System.err.println("Wrong algorithm. Choose from (RC4, Feistel, Hach, CBC, Authentication).");
							}
						
						if (br != null) br.close();	
						
						
						
						
						
						
						
						/**
						 * Send response according to file type
						 */
						/*if(extension.equals("HTML") || extension.equals("TXT")) {
							BufferedReader br = null;
							if (extension.equals("HTML")) {
								File htmlFile = new File(path);
								int htmlFileLength = (int)htmlFile.length();
								//Send to browser/telnet
								out.println(httpVersion + " 200 OK");
								out.println("Content-Type: text/html");
								//out.println("Content-length: " + htmlFileLength);
								out.println("Server: localhost");
								out.println("");
								
								br = new BufferedReader(new FileReader(htmlFile));
							}
							else if (extension.equals("TXT"))
							{
								File textFile = new File(path);
								int textFileLength = (int)textFile.length();
								//Send to browser/telnet
								out.println(httpVersion + " 200 OK");
								out.println("Content-Type: text/plain");
								out.println("Content-length: " + textFileLength);
								out.println("Server: localhost");
								out.println("");
								
								br = new BufferedReader(new FileReader(textFile));
							}
							//send to client
							try {
								while((s = br.readLine()) != null) {
									out.println(s);
								}
							}
							catch (FileNotFoundException e) {
								System.out.print(e.toString());
								out.println("404 not found");
							}
							finally {
								br.close();
							}
						}
						else if (extension.equals("JPG") || extension.equals("JPEG") || extension.equals("GIF"))
						{
							File fileImg = new File(path);
							int fileLength = (int)fileImg.length();
							if (extension.equals("JPG") || extension.equals("JPEG"))
							{
								//Send to browser/telnet
								out.println(httpVersion + " 200 OK");
								out.println("Content-Type: image/jpeg");
								out.println("Content-length: " + fileLength);
								out.println("Server: localhost");
								out.println("");
							}
							else if (extension.equals("GIF"))
							{
								//Send to browser/telnet
								out.println(httpVersion + " 200 OK");
								out.println("Content-Type: image/gif");
								out.println("Content-length: " + fileLength);
								out.println("Server: localhost");
								out.println("");
							}
							//send to client
							try {
								//lecture d'un fichier binaire dans un buffer d'octets
						        byte[] buffer = new byte[fileLength];
						        FileInputStream inputStream = null;
						        try {
						        	inputStream = new FileInputStream(fileImg);
									inputStream.read(buffer);
						        }
								finally {
									inputStream.close();
								}
						        binOut.write(buffer, 0, fileLength);
								
							}
							catch (FileNotFoundException e)
							{
								System.out.print(e.toString());
								out.println("404 not found");
							}
						}
						else {
							out.println("404 not found");
						}*/
			        }
		        }
		        else {
		        	out.println("405 Method not allowed");
		        }
	        }
	        catch (Exception e)
	        {
	        	System.out.print(e.toString());
	        	out.println("500 internal server error");
	        }
	        
	        out.flush();
		    sockClient.close();
		    binOut.close();
	        
	        
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
                WebServer sw = new WebServer();
                sw.start(port_no);
            } 
            catch (NumberFormatException e) {
                System.err.println("Call example: WebServer 5000");
                System.exit(1);
            }
         }
       else {
            System.err.println("Call example: WebServer 5000");
        }
		
	}

}
