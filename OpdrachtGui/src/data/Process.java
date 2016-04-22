package data;

import java.util.ArrayList;
import java.util.List;

public class Process {
	private int pid; 
	private PageTable pageTable;
	private int lastAccesTime;
	private List<Integer>framesFreeAllocated=new ArrayList<Integer>();
	private List<Integer>framesTakenAllocated=new ArrayList<Integer>();
	
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

	public PageTable getPageTable() {
		return pageTable;
	}

	public void setPageTable(PageTable pageTable) {
		this.pageTable = pageTable;
	}
	
	public int giveFrameNumberToFill(){
		if(framesFreeAllocated.isEmpty()){
			return deleteFrameFromRam();
		}
		else return framesFreeAllocated.get(0);
	}
	
	public int deleteFrameFromRam(){
		int LRU=Integer.MAX_VALUE;
		int number=-1;
		PageTableEntry pte=null;
		for(Integer i:framesTakenAllocated){
			PageTableEntry help=pageTable.findPageTableEntry(i);
			if(help.getLastAccessTime()<LRU){
				pte=help;
				LRU=help.getLastAccessTime();
				number=i;
			}
		}
		pte.setPresentBit(false);
		pte.setRamToPersistent(pte.getRamToPersistent()+1);
		return number;
	}
	
	
	
	
}
