import java.net.*;
import java.io.*;

public class Client {
	private Socket client;
	public static final int BUFFER_SIZE = 4096;
	
	public Client(String host, int port) throws UnknownHostException, IOException{
		client = new Socket(host,port);
	}
	public void sendFile (String fileName) throws FileNotFoundException, IOException {
		
		if (client != null && !client.isClosed() && fileName != null) {
			
			File file = new File(fileName);
			
			try (InputStream fileIn = new FileInputStream(file)){
			
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = 0;
				OutputStream serverOut = client.getOutputStream();
			
				while ((bytesRead = fileIn.read(buffer, 0, BUFFER_SIZE)) > 0) {
					serverOut.write(buffer, 0, bytesRead);
				}
				serverOut.flush();
				System.out.println("Client send the file for analyzing...");
			}
		}
	}
	

	public static void main(String[] args) throws IOException{
		Client c = new Client("localhost", 8080);
		
		try {
			c.sendFile("logs_BCS37_20181103_UTF8.txt");
		}
		finally {
			SocketUtil.close(c.client);
		}

	}

}
