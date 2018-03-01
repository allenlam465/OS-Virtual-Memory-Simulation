
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
			else{
				tlbTable[first] = entry;
				first++;
				resetFirst();
			}
		}
	}

	public void resetFirst(){
		if(first == 8){
			first = 0;
		}
	}

	public TLBEntry getEntry(int i) {
		return tlbTable[i];
	}

}
