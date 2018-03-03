import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	
	public CSV csv;
	
	public static void main (String args[]) throws IOException{
		
		OS os = new OS();
		
		os.copyPgFiles();
		
		Scanner s = new Scanner(System.in);
		int rw;
		String input;

		System.out.println("File Name: ");
		//input = s.nextLine();
		input = "test_files/test_1.txt";

		String[] processes = getFileContent(input).split("\\s+");
		
		os.cpu = new CPU(processes);
		os.mmu = new MMU(os.cpu.getProcesses());
		
		for(int i = 0; i < processes.length; i++){
			i = os.mmu.handle(i);
			
			System.out.println("I: " + i);
			
			
			if(os.mmu.checkTLB()){
				System.out.println("Hit found in tlb.");

			}
			else if(os.mmu.checkpgTable()){
				System.out.println("Soft miss found in page table.");

			}
			else{
				System.out.println("Hard miss not in TLB or pgTable.");
				System.out.println("Writing to physical memory.");
				
				
				
				PageTableEntry pgEntry = new PageTableEntry("1", "1", "0");
				os.mmu.getVPT().addEntry(pgEntry, Integer.parseInt(os.mmu.pgNum,16));

				TLBEntry entry = new TLBEntry(os.mmu.pgNum, "1", "1", "0");
				entry.setpageFrameNum(pgEntry.getPageFrameNum());
				os.mmu.getTLB().addEntry(entry);
			}
			
			rw = Integer.parseInt(os.mmu.getRW());
			
			System.out.println("RW: " + os.mmu.getRW());
			
			if(rw == 1){
				i++;
			}
			else{
			
			}
			
			os.instructionNum += 1;
			
			System.out.println(processes[i]);
		}
		
		s.close();
		
	}
	
	private static void write(String pgNum, OS os, int index) throws IOException{

		
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
