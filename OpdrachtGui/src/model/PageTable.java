package model;

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
			pte.setPageNumber(i);
			pte.setPid(pid);
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
		int getal = new Double(pageNumber).intValue();
		PageTableEntry pageTableEntry=null;
		while(!found&&i<pageTable.size()){
			pageTableEntry=pageTable.get(i);
			if(pageTableEntry.getPageNumber()==getal)found=true;
			i++;
		}
		return pageTableEntry;
	}
	
	public PageTableEntry findLeastRecentlyUsed(){
		PageTableEntry leastRecentlyUsed=new PageTableEntry();
		leastRecentlyUsed.setLastAccessTime(Integer.MAX_VALUE);
		for(PageTableEntry pte:pageTable){
			if(pte.isPresentBit()){
				if(pte.getLastAccessTime()<leastRecentlyUsed.getLastAccessTime()){
					leastRecentlyUsed=pte;
				}
			}
		}
		return leastRecentlyUsed;
		
	}
	
	public PageTableEntry get(int d){
		return pageTable.get(d);
	}
}
