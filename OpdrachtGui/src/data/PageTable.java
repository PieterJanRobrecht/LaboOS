package data;

import java.util.List;

public class PageTable {
	private int pid;
	private List<PageTableEntry> pageTable;

	public PageTable() {
		super();
	}

	public List<PageTableEntry> getPageTable() {
		return pageTable;
	}

	public void setPageTable(List<PageTableEntry> pageTable) {
		this.pageTable = pageTable;
	}
	
	
}
