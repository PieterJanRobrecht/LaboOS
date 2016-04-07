package data;

public class Process {
	int pid; 
	PageTable pagetable;
	
	public Process(){
		
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public PageTable getPagetable() {
		return pagetable;
	}

	public void setPagetable(PageTable pagetable) {
		this.pagetable = pagetable;
	}
	
	
	
	
}
