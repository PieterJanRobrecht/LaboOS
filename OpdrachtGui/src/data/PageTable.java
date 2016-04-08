package data;

import java.util.List;

public class PageTable {
	private int pid;
	private int grootte;
	private List<PageTableEntry> pageTable;

	public PageTable(int grootte) {
		this.grootte=grootte;
	}

	public List<PageTableEntry> getPageTable() {
		return pageTable;
	}

	public void setPageTable(List<PageTableEntry> pageTable) {
		this.pageTable = pageTable;
	}

	public PageTableEntry findPage(int pageNumber) {
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
}
