import java.io.*;
import java.net.*;

public class Server {
	private ServerSocket server;
	private boolean isRunning;
	public static final int BUFFER_SIZE = 4096;

	public Server (int port) throws IOException{
		server = new ServerSocket(port);
	}
	
	public void start(){
		
		if(!isRunning){
			isRunning = true;
			System.out.println("Server is listening on port " + this.server.getLocalPort());
		}
	}
	public void stop(){
		if(isRunning){
			isRunning = false;
			SocketUtil.close(this.server);
		}
	}
	
	public void listen (){
		while (isRunning){
			try {
				Socket client = server.accept();
				processClient(client);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	private void processClient(Socket client) throws IOException {
		try {
			InputStream inData = client.getInputStream();
			String file = acceptFile(inData);
			analyzeFile(file);
			
			System.out.print("The analyzed file(interpretData)is ready!");
		}
	 finally {
		SocketUtil.close(client);
	 }
	}


	private void analyzeFile(String file) throws IOException {
		 FileAnalyze.analyze(file);
	}
	


	private String acceptFile(InputStream inData) throws IOException {
		
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = 0;
		File file = new File("received.txt");
		
		try (OutputStream fileOut = new FileOutputStream(file)) {
			while ((bytesRead = inData.read(buffer, 0, BUFFER_SIZE)) > 0) {
				fileOut.write(buffer, 0, bytesRead);
			}
			fileOut.flush();
		}
		System.out.println("Accepted file from client!");
		System.out.println("Analyizing...");
		
		return "received.txt";
	}

	public static void main(String[] args) throws IOException {
		Server s = new Server(8080);
		try {
			if (s != null) {
				s.start();
				s.listen();
			}
		} finally {
			if (s != null) {
				s.stop();
			}
		}
	}

}
