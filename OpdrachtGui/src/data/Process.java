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
	
	
	
	public int giveFrameNumberToFill(boolean eigen){
		if(framesFreeAllocated.isEmpty()){
			return deleteFrameFromRam(eigen);
		}
		else{
			int number=framesFreeAllocated.get(0);
			framesFreeAllocated.remove(0);
			if(eigen){
				framesTakenAllocated.add(number);
			}	
			return number;
		}
	}
	
	public int deleteFrameFromRam(boolean eigen){
		int LRU=Integer.MAX_VALUE;
		int number=-1;
		int index=-1;
		PageTableEntry pte=null;
		for(int i=0;i<framesTakenAllocated.size();i++){
			PageTableEntry help=pageTable.findPageTableEntry(i);
			if(help.getLastAccessTime()<LRU){
				pte=help;
				LRU=help.getLastAccessTime();
				number=framesTakenAllocated.get(i);
				index=i;
				
			}
		}
		pte.setPresentBit(false);
		pte.setRamToPersistent(pte.getRamToPersistent()+1);
		if(!eigen){
			framesTakenAllocated.remove(index);
		}
		return number;
	}

	public List<Integer> getFramesFreeAllocated() {
		return framesFreeAllocated;
	}

	public List<Integer> getFramesTakenAllocated() {
		return framesTakenAllocated;
	}

	public void setFramesFreeAllocated(List<Integer> framesFreeAllocated) {
		this.framesFreeAllocated = framesFreeAllocated;
	}

	public void setFramesTakenAllocated(List<Integer> framesTakenAllocated) {
		this.framesTakenAllocated = framesTakenAllocated;
	}
	
	
	
	
}
