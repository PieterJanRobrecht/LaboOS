package data;

public class Process {
	int pid; 
	PageTable pageTable;
	int lastAccesTime;
	
	public Process(){
	}
	
	public Process(int pid,int grootteVirtueel){
		this.pid=pid;
		pageTable=new PageTable(grootteVirtueel);
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public PageTable getPagetable() {
		return pageTable;
	}

	public void setPagetable(PageTable pagetable) {
		this.pageTable = pagetable;
	}

	public int getLastAccesTime() {
		return lastAccesTime;
	}

	public void setLastAccesTime(int lastAccesTime) {
		this.lastAccesTime = lastAccesTime;
	}
	
	
	
	
}
