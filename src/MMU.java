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
		
		System.out.println(i);
		rw = processes[i];
		
		i++;
		
		pgNum = getPgNum(processes[i]);
		offset = getOffset(processes[i]);

		return i;
	}

	public boolean checkpgTable() {
		return pageTable.inPT(pgNum);
	}

	public boolean checkTLB() {
		return tlb.inTLB(pgNum);
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
