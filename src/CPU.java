import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CPU {

	protected String[] processes;
	protected MMU mmu;
	protected PhyMem ram;
	protected TLBCache tlb;
	

	public static void main(String[] arg) throws IOException {
		Scanner s = new Scanner(System.in);
		int write;
		String input;

		System.out.println("File Name: ");
		input = s.nextLine();

		String[] processes = getFileContent(input).split("\\s+");

		//Sending virtual addresses to the MMU
		MMU mmu = new MMU(processes);

		for(int i = 0; i < processes.length; i++){
			i = mmu.handle(i);
			
			write = Integer.getInteger(mmu.getRW());
			
			if(write == 1){
				
			}
			else{
				
			}
			
		}

		s.close();
	}

	private void sendMMU(String[] process){
		mmu = new MMU(process);
	}

	//Obtain values in the test files (The virtual memory address)
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
