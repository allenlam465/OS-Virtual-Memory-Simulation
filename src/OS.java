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

	public void resetR(){
		if(instructionNum == 20){
			for(int i = 0; i < pageTable.pgTable.length; i++){
				if(pageTable.pgTable[i] != null){
					pageTable.pgTable[i].setReference("0");
				}
			}
		}
	}
	
	public PageTableEntry evict(){
		
		PageTableEntry output;
		
		while (true){
			if(clock.hand.data.getReference().equals("0")){
				output = clock.hand.data;
				clock.hand.data = null;
				return output;
			}
			else if(clock.hand.data.getReference().equals("1")){
				clock.hand.data.setReference("0");

				clock.hand = clock.hand.next;

			}
		}

	}
}
