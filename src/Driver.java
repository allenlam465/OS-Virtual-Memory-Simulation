import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Driver {

	public static void main (String args[]) throws IOException{

		OS os = new OS();

		//Copy pages from page_files to page_files_copy for each run
		os.copyPgFiles();

		Scanner s = new Scanner(System.in);
		int output, rw ,index;
		TLBEntry evicted = null;
		String input, line = null;

		System.out.println("File Name: ");
		input = s.nextLine();

		os.csv = new CSV(input);

		//Obtain processes to be sent to CPU -> MMU
		String[] processes = getFileContent(input).split("\\s+");

		os.cpu = new CPU(processes);
		os.mmu = new MMU(os.cpu.getProcesses());

		//Processes are run one at time by checking TLB first then PageTable to determine type
		//of miss or a hit occurs.
		for(int i = 0; i < processes.length; i++){
			i = os.mmu.handle(i);

			if(os.mmu.checkTLB()){
				//System.out.println("Hit found in tlb.");

				rw = Integer.parseInt(os.mmu.getRW());

				if(rw == 1){
					i++;
					
					line += processes[i] + ", 0," + "0," + "1,";
					
					os.mmu.getEntry().setReference("1");
					os.mmu.getEntry().setDirty("1");
					
					output = os.write(os.mmu.pgNum, os.mmu.offset, Integer.parseInt(processes[i]));
					
					line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + "None , None";


				}
				else{
					
					output = os.read(os.mmu.pgNum, os.mmu.offset);
					
					line += output + ", 0," + "0," + "1,";
					
					os.mmu.getEntry().setReference("1");
					os.mmu.getEntry().setDirty("1");
					
					line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + "None , None";
				}

			}
			else if(os.mmu.checkpgTable()){
				//System.out.println("Soft miss found in page table.");
				//System.out.println("Adding to TLB.");
				index = os.ram.freeIndex();

				if(index != -1){
					os.ram.loadPage(os.mmu.pgNum, index);
				}
				else{
					evicted = os.evict();
					index = os.ram.freeIndex();
					os.ram.loadPage(os.mmu.pgNum, index);
				}

				rw = Integer.parseInt(os.mmu.getRW());

				if(rw == 1){
					i++;
					
					os.mmu.getEntry().setReference("1");
					os.mmu.getEntry().setDirty("1");

					os.mmu.addTLB("1", "1", "1", Integer.toString(index));

					if(os.clock.isEmpty()){
						os.clock.insertFirst(os.mmu.tlbEntry);
					}
					else{
						os.clock.insertNext(os.mmu.tlbEntry);
					}
					
					output = os.write(os.mmu.pgNum, os.mmu.offset, Integer.parseInt(processes[i]));
					
					if(evicted != null){
						line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + evicted.getPageFrameNum() + "," + evicted.getDirty();

					}
					else{
						line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + "None , None";

					}

				}
				else{
					os.mmu.addTLB("1", "1", "0", Integer.toString(index));

					if(os.clock.isEmpty()){
						os.clock.insertFirst(os.mmu.tlbEntry);
					}
					else{
						os.clock.insertNext(os.mmu.tlbEntry);
					}
					
					output = os.read(os.mmu.pgNum, os.mmu.offset);

					if(evicted != null){
						line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + evicted.getPageFrameNum() + "," + evicted.getDirty();

					}
					else{
						line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + "None , None";

					}
				}

			}
			else{
				//System.out.println("Hard miss not in TLB or pgTable.");
				//System.out.println("Writing to physical memory.");

				index = os.ram.freeIndex();

				if(index != -1){
					os.ram.loadPage(os.mmu.pgNum, index);
				}
				else{
					evicted = os.evict();
					index = os.ram.freeIndex();
					os.ram.loadPage(os.mmu.pgNum, index);
				}

				rw = Integer.parseInt(os.mmu.getRW());

				if(rw == 1){
					i++;
									
					os.mmu.addPageTable("1", "1", "1", Integer.toString(index));
					os.mmu.addTLB("1", "1", "1", Integer.toString(index));

					if(os.clock.isEmpty()){
						os.clock.insertFirst(os.mmu.tlbEntry);
					}
					else{
						os.clock.insertNext(os.mmu.tlbEntry);
					}
					
					output = os.write(os.mmu.pgNum, os.mmu.offset, Integer.parseInt(processes[i]));
					
					if(evicted != null){
						line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + evicted.getPageFrameNum() + "," + evicted.getDirty();

					}
					else{
						line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + "None , None";

					}
					
				}
				else{

					os.mmu.addPageTable("1", "1", "0", Integer.toString(index));

					os.mmu.addTLB("1", "1", "0", Integer.toString(index));
					
					output = os.read(os.mmu.pgNum, os.mmu.offset);

					if(os.clock.isEmpty()){
						os.clock.insertFirst(os.mmu.tlbEntry);
					}
					else{
						os.clock.insertNext(os.mmu.tlbEntry);
					}
										
					if(evicted != null){
						line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + evicted.getPageFrameNum() + "," + evicted.getDirty();

					}
					else{
						line = os.mmu.getAddress() + "," + os.mmu.getRW() + "," + output + ",1," + "0," + "0," + "None , None";

					}
				}

			}
			
			os.csv.write(line);

			os.instructionNum += 1;
			os.resetR();
		}
		System.out.println("Processing complete.\nNew page files within page_files_copy directory and CSV files are located within the test_files directory.");

		s.close();

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
