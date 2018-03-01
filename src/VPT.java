public class VPT {

	protected PageTableEntry[] pgTable = new PageTableEntry[256];

	public VPT() {

	}

	//Use the virtual page number to index the entry
	protected void addEntry(PageTableEntry entry,int vPage){
		pgTable[vPage] = entry;
	}
	
	protected boolean isEntry(int index){
		if(pgTable[index] == null){
			return false;
		}
		
		return true;
	}

}
