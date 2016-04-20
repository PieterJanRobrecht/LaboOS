package data;

import java.util.ArrayList;
import java.util.List;

public class PageTable {
	private int pid;
	private int grootte;
	private List<PageTableEntry> pageTable;

	public PageTable(int grootte) {
		this.grootte=grootte;
		pageTable = new ArrayList<PageTableEntry>();
		for(int i = 0;i<grootte;i++){
			PageTableEntry pte = new PageTableEntry();
			pte.setPageNumber(i+1);
			pageTable.add(pte);
		}
	}

	public List<PageTableEntry> getPageTable() {
		return pageTable;
	}

	public void setPageTable(List<PageTableEntry> pageTable) {
		this.pageTable = pageTable;
	}

	public PageTableEntry findPageTableEntry(double pageNumber) {
		int i=0;
		boolean found=false;
		PageTableEntry pageTableEntry=null;
		while(!found){
			pageTableEntry=pageTable.get(i);
			if(pageTableEntry.getFrameNumber()==pageNumber)found=true;
			i++;
		}
		return pageTableEntry;
	}
	
	public PageTableEntry findLeastRecentlyUsed(){
		return null;
		
	}
	
	public PageTableEntry get(double d){
		int i = (int) d;
		return pageTable.get(i);
	}
}
