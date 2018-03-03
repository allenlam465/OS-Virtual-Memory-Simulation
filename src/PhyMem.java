import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PhyMem {

	protected int[][] ram = new int[16][256];

	public PhyMem(){

	}

	public void loadPage(String pgNum, int index) throws IOException{
		String[] tokens = getFileContent("page_files_copy/" + pgNum + ".pg").split("\\s+");
		for(int i = 0; i < tokens.length; i++){
			ram[index][i] = Integer.parseInt(tokens[i]);
		}
	}
	
	public void evictTable(int index){
		for(int i = 0; i < ram[0].length; i++){
			ram[index][i] = 0;
		}
	}

	public void pageToFile(String pgNum, int index) throws IOException{
		FileWriter fstream = new FileWriter(pgNum + ".pg", true);
		BufferedWriter ostream = new BufferedWriter(fstream);
		
		for(int i = 0; i < ram[0].length; i++){
			ostream.write(Integer.toString(ram[index][i]));
		}
		
		ostream.close();
			
	}

	private static String getFileContent(String filePath) throws IOException {
		try
		(
			BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				if(!line.isEmpty()) {
					sb.append(line);
					sb.append(System.lineSeparator());

					line = br.readLine();
				}
			}

			String everything = sb.toString();

			return everything;
		}
		catch(FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e.getMessage());
		}

		return null;
	}
}
