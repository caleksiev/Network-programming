import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class User {
	
	private DatagramSocket ds;
	private String username;
	private String userHost;
	private ChatServer charServer;
	
	public User(String username,String userHost) throws SocketException {
		this.username = userHost;
		this.userHost = userHost;
		ds = new DatagramSocket();
		charServer = new ChatServer();
	}
	
	public String getHost(){
		return userHost;
	}
	
	public void joinRoom(String roomName) throws IOException {
		charServer.joinToChatRoom(roomName, userHost, username);
	}
	
	public void leaveRoom(String roomName) throws IOException {
		charServer.leaveChatRoom(roomName, userHost);
	}
	
	public void send(String msg,String host, int port) throws IOException{
		
		byte[] buf = msg.getBytes();
		DatagramPacket p = new DatagramPacket(buf, buf.length);
		
		InetAddress a = InetAddress.getByName(host);
		p.setAddress(a);
		p.setPort(port);
		
		ds.send(p);
	}
	
	public void sendFile() throws FileNotFoundException, IOException {
		
		File f = new File("video1.mp4");
		
		try (InputStream fileIn = new FileInputStream(f)) {

			InetAddress address = InetAddress.getByName("localhost");
			int port = 8080;
		
			byte[] data = new byte[1024];
			int bytesRead = 0;
			
			while ((bytesRead = fileIn.read(data, 0, 1024)) > 0) {
				DatagramPacket p = new DatagramPacket(data, 0, bytesRead, address, port);
				ds.send(p);
			}		
		}
	}

	public void receive() throws IOException{
		
		DatagramPacket r = new DatagramPacket(new byte[1024], 1024);
		ds.receive(r);
		System.out.println(new String(r.getData(), r.getOffset(), r.getLength()));
	}
}
