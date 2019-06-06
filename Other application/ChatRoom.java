import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom {
	
	
	private String chatRoomName;
	private MulticastSocket ms;
	private Map<String, User> users;
	
	
	public ChatRoom(String chatRoomName, int port) throws IOException{
		this.chatRoomName = chatRoomName;
		users = new HashMap<String, User>();
		ms = new MulticastSocket(port);
	}
	public String getRoomName(){
		return chatRoomName;
	}
	
	public void joinToChat(String host,String userName) throws IOException{
		User newUser = new User(userName,host);
		users.put(host, newUser);
		
		InetAddress group = InetAddress.getByName(host);
		ms.joinGroup(group);
	}
	
	public void leaveChat(String userHost) throws IOException{
		User user = users.get(userHost);
		if(user != null){
			InetAddress userAddress = InetAddress.getByName(userHost);
			ms.leaveGroup(userAddress);
			users.remove(userHost);
		}
	}
	
	public void listen() throws IOException
	{
		while(true){
			receiveUDPMessage();
		}
	}
		
	private void receiveUDPMessage() throws IOException {
		DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
		ms.receive(p);
		System.out.println("Server received: " + new String(p.getData(), p.getOffset(), p.getLength()));
		
	}
	
	private void receiveFile() throws IOException {
		File f = new File("video.mp4");
		
		try (OutputStream fileOut = new FileOutputStream(f)) {
			
			DatagramPacket p = new DatagramPacket(new byte[8], 0, 8);
			ms.receive(p);			
			byte[] response = "SUCCESS".getBytes();
			p = new DatagramPacket(response, 0, response.length, p.getAddress(), p.getPort());
			ms.send(p);	
		}
	}
	public void close(){
		ServerUtils.close(ms);
	}

}
