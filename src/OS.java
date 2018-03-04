import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class OS {

	public CSV csv;
	protected VPT pageTable = new VPT();
	protected CPU cpu;
	protected MMU mmu;
	protected PhyMem ram = new PhyMem();
	protected Clock clock = new Clock();

	protected int instructionNum = 0;

	public void copyPgFiles() throws IOException{
		Path srcdir = Paths.get("page_files");
		Path dstdir = Paths.get("page_files_copy");

		for (final Path path: Files.newDirectoryStream(srcdir))
			Files.copy(path, dstdir.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
	}

	//Reset r-bits every 5 instructions
	public void resetR(){
		if(instructionNum == 5){
			for(int i = 0; i < pageTable.pgTable.length; i++){
				if(pageTable.pgTable[i] != null){
					pageTable.pgTable[i].setReference("0");
				}
			}
		}
	}

	//Read value from physical memory
	public int read(String vPage, String offset){
		int val = ram.getData(Integer.parseInt(mmu.tlb.getFrame(vPage)), Integer.parseInt(offset, 16));

		return val;
	}

	//Writes value to physical memory
	public int write(String vPage, String offset, int value){
		ram.ram[(Integer.parseInt(mmu.tlb.getFrame(vPage)))][Integer.parseInt(offset, 16)] = value;
		return value;
	}

	//Evicts page based on clock algorithm
	public TLBEntry evict() throws NumberFormatException, IOException{

		TLBEntry output;

		while (true){
			if(clock.hand.data.getReference().equals("0")){
				output = clock.hand.data;
				
				if(output.getDirty().equals("1")){
					ram.pageToFile(output.getVPage(), Integer.parseInt(output.getPageFrameNum()));
					mmu.pageTable.getEntry(output.getVPage()).setDirty("0");
				}
				
				ram.evictTable(Integer.parseInt(output.getPageFrameNum()));
				clock.hand.data = null;
				clock.hand.next = clock.hand.next;
				return output;
			}
			else if(clock.hand.data.getReference().equals("1")){
				clock.hand.data.setReference("0");
				clock.hand = clock.hand.next;

			}
		}

	}

}
