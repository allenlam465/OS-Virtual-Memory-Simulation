
public class TLBEntry {

	protected String entry[] = new String[5];

	public TLBEntry() {

	}

	public TLBEntry(String vPage, String valid, String reference, String dirty, String pageFrame) {
		entry[0] = vPage;
		entry[1] = valid;
		entry[2] = reference;
		entry[3] = dirty;
		entry[4] = pageFrame;
	}
	
	public void print(){
		System.out.println("V Address: " + entry[0] + " Valid: " + entry[1] + " Reference: " + entry[2] + " Dirty: " + entry[3] + " Frame#: " + entry[4]);
	}

	public String getVPage() {
		return entry[0];
	}

	protected void setVPage(String vPage) {
		entry[0] = vPage;
	}

	public String getValid() {
		return entry[1];
	}

	protected void setValid(String valid) {
		entry[1] = valid;
	}

	public String getReference() {
		return entry[2];
	}

	protected void setReference(String reference) {
		entry[2] = reference;
	}

	public String getDirty() {
		return entry[3];
	}

	protected void setDirty(String dirty) {
		entry[3] = dirty;
	}

	public String getPageFrameNum() {
		return entry[4];
	}

	protected void setpageFrameNum(String pageFrameNum) {
		entry[4] = pageFrameNum;
	}
}
