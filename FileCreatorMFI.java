import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class FileCreatorMFI {
	private Integer specialId;
	private ArrayList<HashMap<String, Integer>> listOfMaps;
	private HashMap<Integer, Attribute> keyStore;
	
	
	
	public FileCreatorMFI(int how){
		specialId = 1;
		listOfMaps = new ArrayList <HashMap<String, Integer>>(how);
		keyStore = new HashMap<Integer, Attribute>();
		
		for (int i = 0 ; i < how ; ++i){
		listOfMaps.add(new HashMap<String, Integer>());
		}
	}
	
	public void writeToFileMFI(String[] elem) throws IOException{
		FileWriter file = new FileWriter("result.txt",true);
		ArrayList <Integer>numberStore = new ArrayList<Integer>();//store the special number for MFI alg
		for (int i = 0 ; i<elem.length;++i){
			
			int num = addHourAndGetspecialId (elem[i],i);//get the special number of every attribute
			Attribute atr = keyStore.get(num);
			
			
			if(atr == null){
				keyStore.put(num, new Attribute(elem[i],i));
			}
			else//nothing
				;
			
			numberStore.add(num);
		}
		Collections.sort(numberStore);
		
		for (int num : numberStore){
			file.write(String.valueOf(num));
			file.write(" ");
		}
		file.write(System.lineSeparator());
		if(file!= null)
			file.close();
	}
	public void interpretDataMFI() throws IOException{
		FileReader inputFile = new FileReader("output.txt");
		FileWriter outputFile = new FileWriter("interpretData.txt");
		BufferedReader buffInputStream = new BufferedReader(inputFile);
		
		String line = null;
		int counter = 1;
		while((line = buffInputStream.readLine()) != null && line != ""){
			outputFile.write(counter+ ":");
			++counter;
			outputFile.write(System.lineSeparator());
			String [] elements = line.split(" ");
			int length = elements.length;
			for(int i = 0 ; i<length-3; ++i ){
				int num = Integer.parseInt(elements[i]);
				Attribute atr = keyStore.get(num);
				
				outputFile.write(atr.getType() + ":");
				outputFile.write(atr.getName() + ",");
			}
			outputFile.write(elements[length-1] + "operation!");
			outputFile.write(System.lineSeparator());
		}
		if(outputFile != null)
			outputFile.close();
		if(buffInputStream != null)
			buffInputStream.close();
	}
	private Integer addHourAndGetspecialId (String text,int index) {
		Integer  specialIdOf = listOfMaps.get(index).get(text);
		if (specialIdOf == null){
			listOfMaps.get(index).put(text, specialId);
		    return specialId++;
		}
		else {
			return specialIdOf;
		}
	}
}
