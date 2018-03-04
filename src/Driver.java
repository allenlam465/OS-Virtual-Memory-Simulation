import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Driver {

	public static void main (String args[]) throws IOException{

		OS os = new OS();

		os.copyPgFiles();

		Scanner s = new Scanner(System.in);
		int rw ,index;
		String input, line;

		System.out.println("File Name: ");
		//input = s.nextLine();
		input = "test_files/test_1.txt";

		os.csv = new CSV(input);

		String[] processes = getFileContent(input).split("\\s+");

		os.cpu = new CPU(processes);
		os.mmu = new MMU(os.cpu.getProcesses());

		for(int i = 0; i < processes.length; i++){
			System.out.println(processes[i]);
			i = os.mmu.handle(i);

			if(os.mmu.checkTLB()){
				System.out.println("Hit found in tlb.");
				System.out.println(os.mmu.getAddress());

				line = os.mmu.getAddress() + os.mmu.getRW() + "0" + "0" + "1";

				rw = Integer.parseInt(os.mmu.getRW());

				if(rw == 1){
					i++;
					os.mmu.offset += "." + processes[i];

				}
				else{

				}

			}
			else if(os.mmu.checkpgTable()){
				System.out.println("Soft miss found in page table.");
				System.out.println(os.mmu.getAddress());


				line = os.mmu.getAddress() + os.mmu.getRW() + "1" + "0" + "0";

				line = os.mmu.getAddress() + os.mmu.getRW();

				rw = Integer.parseInt(os.mmu.getRW());

				if(rw == 1){
					i++;
					os.mmu.offset += "." + processes[i];

				}
				else{

				}

			}
			else{
				System.out.println("Hard miss not in TLB or pgTable.");
				System.out.println("Writing to physical memory.");

				index = os.ram.freeIndex();

				if(index != -1){
					os.ram.loadPage(os.mmu.pgNum, index);
				}
				else{
					os.ram.evictTable(Integer.parseInt(os.evict().getPageFrameNum()));
					index = os.ram.freeIndex();
					os.ram.loadPage(os.mmu.pgNum, index);
				}

				rw = Integer.parseInt(os.mmu.getRW());

				if(rw == 1){
					i++;

					os.mmu.offset += "." + processes[i];
					os.mmu.addPageTable("1", "1", "1", Integer.toString(index));

					os.mmu.addTLB("1", "1", "1", Integer.toString(index));

					if(os.clock.isEmpty()){
						os.clock.insertFirst(os.mmu.pgEntry);
					}
					else{
						os.clock.insertNext(os.mmu.pgEntry);
					}

				}
				else{
					os.mmu.addPageTable("1", "1", "0", Integer.toString(index));

					os.mmu.addTLB("1", "1", "0", Integer.toString(index));

					if(os.clock.isEmpty()){
						os.clock.insertFirst(os.mmu.pgEntry);
					}
					else{
						os.clock.insertNext(os.mmu.pgEntry);
					}

					System.out.println(os.mmu.getAddress());

					line = os.mmu.getAddress() + os.mmu.getRW() + "0" + "1" + "0";

				}

			}

			os.instructionNum += 1;
			os.resetR();
		}

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
