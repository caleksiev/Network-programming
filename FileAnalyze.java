import java.io.*;


public class FileAnalyze {

	public static void analyze(String fileName) throws IOException {
		System.out.println(fileName);
		FileCreatorMFI outputFile = new FileCreatorMFI (8);
		File file = null;
		BufferedReader buffInputStream = null;
		InputStreamReader inputStream = null;

		try {
			file = new File (fileName);
			inputStream = new InputStreamReader(new FileInputStream(file),"UTF8");
			buffInputStream = new BufferedReader(inputStream);
			
			
			String line = null;
			while((line = buffInputStream.readLine()) != null )
			{
				String [] elements = line.split(",",8);
				elements[0] = extractData(elements);
				elements[1] = extractHour (elements);
				
				outputFile.writeToFileMFI(elements);//for every row from a file every component is decode to number
				
			}
			MainTestCharmMFI_saveToFile.main(null);//make the MFI file

			outputFile.interpretDataMFI();
		}
		catch(FileNotFoundException e){
			System.out.println( "File Disappeared" );
		}
		finally {
			if(inputStream!=null){
			inputStream.close();}
		}
		
	}
	
	private static String extractHour(String[] elements) {
		int lengthOfString = elements[1].length();
		String correctTime = elements[1].substring(1,lengthOfString-1);
		
		return correctTime;
	}
	
	private static String extractData(String[] elements) {
		
		String correctData = elements[0].substring(1);
		
		return correctData;
	}

}
