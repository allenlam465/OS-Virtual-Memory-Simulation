
public class TLBCache {

	protected TLBEntry tlbTable[] = new TLBEntry[8];
	//Tracks the first entry for FIFO
	protected int first = 0;

	public TLBCache(){

	}

	public void addEntry(TLBEntry entry) {
		for(int i = 0; i < tlbTable.length; i++) {
			if(tlbTable[i] == null) {
				tlbTable[i] = entry;
				break;
			}
			else if(i == 7){
				tlbTable[first] = entry;
				first++;
				resetFirst();
			}
		}
	}
	
	public boolean inTLB(String vAddr) {
		for(int i = 0; i < tlbTable.length; i++) {
			if(tlbTable[i] != null && vAddr.equals(tlbTable[i].getVPage())) {
				return true;
			}
		}
		
		return false;
	}

	public void resetFirst(){
		if(first == 8){
			first = 0;
		}
	}
	
	public String getEntry(String vAddr) {
		for(int i = 0; i < tlbTable.length; i++) {
			if(tlbTable[i] != null && vAddr.equals(tlbTable[i].getVPage())) {
				return tlbTable[i].getPageFrameNum();
			}
		}
		
		return null;
	}

	public TLBEntry getEntry(int i) {
		return tlbTable[i];
	}
	
	public void printTable() {
		for (int i = 0; i < tlbTable.length; i++) {
			if(tlbTable[i] != null){
				System.out.print(i + " ");
				tlbTable[i].print();
			}
		}
	}
	
	public boolean isFull(){
		for(int i = 0; i < tlbTable.length; i++){
			if(tlbTable[i] == null){
				return false;
			}
		}
		
		return true;
	}

}
