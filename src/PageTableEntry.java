
public class PageTableEntry {

	private String entry[] = new String[4];

	public PageTableEntry() {

	}

	public PageTableEntry(String valid, String reference, String dirty) {
		entry[0] = valid;
		entry[1] = reference;
		entry[2] = dirty;
	}
	
	public void print(){
		System.out.println("Valid: " + entry[0] + "Reference: " + entry[1] + "Dirty: " + entry[2] + "Frame#: " + entry[3]);
	}

	public String getValid() {
		return entry[0];
	}

	private void setValid(String valid) {
		entry[0] = valid;
	}

	public String getReference() {
		return entry[1];
	}

	private void setReference(String reference) {
		entry[1] = reference;
	}

	public String getDirty() {
		return entry[2];
	}

	private void setDirty(String dirty) {
		entry[2] = dirty;
	}

	public String getPageFrameNum() {
		return entry[3];
	}

	protected void setpageFrameNum(int pageFrameNum) {
		entry[3] = Integer.toString(pageFrameNum);
	}


}
