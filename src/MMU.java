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

			if(tlb.inTLB(pgNum)){
				//Output info to csv
				System.out.println("Found in tlb a hit.");
			}
			else if(pageTable.inPT(pgNum)){
				System.out.println("Found in pageTable a soft miss.");
			}
			else{
				System.out.println("Hard Miss Read.");
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

			if(tlb.inTLB(pgNum)){
				//Output info to csv
				System.out.println("Found in tlb a hit.");
				i++;
				i++;
			}
			else if(pageTable.inPT(pgNum)){
				System.out.println("Found in pageTable a soft miss.");
				i++;
				i++;
			}
			else{
				System.out.println("Hard Miss Write.");
				System.out.println("Not in page table. Obtaining page from disk.");

				i++;

				pgNum = getPgNum(processes[i]);
				offset = getOffset(processes[i]);

				i++;

				offset += ("." + processes[i]);

				PageTableEntry pgEntry = new PageTableEntry("1", "1", "1");
				pageTable.addEntry(pgEntry, Integer.parseInt(pgNum,16));

				TLBEntry entry = new TLBEntry(pgNum, "1", "1", "1");
				entry.setpageFrameNum(pgNum);
				tlb.addEntry(entry);
			}

		}

		pageTable.printTable();
		tlb.printTable();

		return i;
	}

	public boolean checkpgTable(String vAddr) {
		return pageTable.inPT(vAddr);
	}

	public boolean checkTLB(String vAddr) {
		return tlb.inTLB(vAddr);
	}

	public VPT getVPT(){
		return pageTable;
	}

	public TLBCache getTLB(){
		return tlb;
	}

	public String getRW(){
		return rw;
	}

	public String getPgNum(String vAddr){
		return vAddr.substring(0,2);
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
