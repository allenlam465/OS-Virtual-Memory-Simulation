public class MMU {

	private String[] processes; //aka virtual addresses
	private VPT pageTable = new VPT();
	protected TLBCache tlb = new TLBCache();
	

	public String rw, pgNum, offset;

	//MMU get all processes
	public MMU(String[] process) {
		processes = process;
	}

	//Processes are handled by MMU to get offset and virtual pgNum
	//Offset at end of the physical address,
	public int handle(int i) {
		rw = processes[i];

		if(rw.equals("0")) {
			i++;

			pgNum = getPgNum(processes[i]);
			offset = getOffset(processes[i]);

			if(inPageTable(pgNum)){
				//Output info to csv
			}
			else{

				System.out.println("Not in page table. Obtaining page from disk.");

				PageTableEntry pgEntry = new PageTableEntry("1", "1", "0");
				pageTable.addEntry(pgEntry, Integer.parseInt(pgNum,16));

				TLBEntry entry = new TLBEntry(pgNum, "1", "1", "0");
				entry.setpageFrameNum(pgEntry.getPageFrameNum());
				tlb.addEntry(entry);

				//Output info to csv
			}
		}
		else if(rw.equals("1")) {

			System.out.println("Not in page table. Obtaining page from disk.");
			
			i++;

			pgNum = getPgNum(processes[i]);
			offset = getOffset(processes[i]);

			i++;

			offset += ("." + processes[i]);

			PageTableEntry pgEntry = new PageTableEntry("1", "1", "1");
			pageTable.addEntry(pgEntry, Integer.parseInt(pgNum,16));

			TLBEntry entry = new TLBEntry(pgNum, "1", "1", "1");
			tlb.addEntry(entry);

		}

		return i;
	}
	
	public VPT getVPT(){
		return pageTable;
	}
	
	public TLBCache getTLB(){
		return tlb;
	}

	public boolean inPageTable(String pgNum){
		return pageTable.isEntry(Integer.parseInt(pgNum,16));
	}
	
	public String getRW(){
		return rw;
	}

	public String getPgNum(String vAddr){
		return vAddr.substring(2);
	}
	
	public String getPgNum(){
		return pgNum;
	}

	public String getOffset(String vAddr){
		return vAddr.substring(2,4);
	}
	
	public String getOffset(){
		return offset;
	}

}