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

	public void findPage(long address) {
		int i=0;
		boolean found=false;
		while(!found){
			PageTableEntry pageTableEntry=pageTable.get(i);
			if(pageTableEntry.getFrameNumber()==adress/)
		}
		
	}
	
	
}
