import java.io.Closeable;
import java.io.IOException;

public class SocketUtil {
	
	public static void close(Closeable c){
		
		try{	
			if(c != null){
				c.close();
			}
		}
		catch(IOException e){
			//
		}
	}

}
