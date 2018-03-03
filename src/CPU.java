public class CPU {

	protected String[] processes;

	public CPU(){		
	}
	
	public CPU(String[] process){
		processes = process;
	}
	
	public void setProcesses(String[] process){
		processes = process;
	}
	
	
	public String[] getProcesses(){
		return processes;
	}
}
