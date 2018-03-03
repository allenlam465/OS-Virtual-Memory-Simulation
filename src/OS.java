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

	public void copyPgFiles() throws IOException{
		Path srcdir = Paths.get("page_files");
		Path dstdir = Paths.get("page_files_copy");

		for (final Path path: Files.newDirectoryStream(srcdir))
		    Files.copy(path, dstdir.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
	}
	
	public void clockAlg(){
		
	}
	
}
