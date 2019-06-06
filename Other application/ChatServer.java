import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class ChatServer {
	private Map<String, ChatRoom> rooms;
	
	public ChatServer () {
		rooms = new HashMap<String, ChatRoom>();
	}
	
	public void addRoom (String host,int port,String roomName) throws IOException{
		rooms.put(host, new ChatRoom(roomName,port));
	}
	
	public void joinToChatRoom(String roomName,String host,String userName) throws IOException{
		ChatRoom room = rooms.get(roomName);
		if(room != null) {
			room.joinToChat(host, userName);
		}
	}
	
	public void leaveChatRoom(String roomName,String userHost) throws IOException{
		ChatRoom room = rooms.get(roomName);
		if(room != null){
			room.leaveChat(userHost);
		}
	}
	
	public void listenInRoom(String roomName) throws IOException{
		ChatRoom room = rooms.get(roomName);
		if(room != null){
			room.listen();
		}
	}
	
	public void closeRoom(String roomName){
		ChatRoom room = rooms.get(roomName);
		if(room != null){
			room.close();
		}
	}
}

