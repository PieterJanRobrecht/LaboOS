package data;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Process {
	private int pid; 
	private PageTable pageTable;
	private int lastAccesTime;
	private List<Integer>framesFreeAllocated=new ArrayList<Integer>();
	private List<Integer>framesTakenAllocated=new ArrayList<Integer>();
	
	private TableView<PageTableEntry> table;
	
	public Process(int pid,int grootteVirtueel){
		this.pid=pid;
		pageTable=new PageTable(grootteVirtueel);
		initTable();
	}

	private void initTable() {
		table = new TableView<PageTableEntry>();
		
		//Maken van de kolommen
		TableColumn<PageTableEntry, Integer> clmn = new TableColumn<PageTableEntry, Integer>("pageNumber");
		clmn.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("pageNumber"));
		table.getColumns().addAll(clmn);
		
		TableColumn<PageTableEntry, Boolean> clmn1 = new TableColumn<PageTableEntry, Boolean>("modifyBit");
		clmn1.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Boolean>("modifyBit"));
		table.getColumns().addAll(clmn1);
		
		TableColumn<PageTableEntry, Boolean> clmn2 = new TableColumn<PageTableEntry, Boolean>("presentBit");
		clmn2.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Boolean>("presentBit"));
		table.getColumns().addAll(clmn2);
		
		TableColumn<PageTableEntry, Integer> clmn3 = new TableColumn<PageTableEntry, Integer>("lastAccessTime");
		clmn3.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("lastAccessTime"));
		table.getColumns().addAll(clmn3);
		
		TableColumn<PageTableEntry, Integer> clmn4 = new TableColumn<PageTableEntry, Integer>("frameNumber");
		clmn4.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("frameNumber"));
		table.getColumns().addAll(clmn4);
		
		//Koppelen van de data aan de tabel
		List<PageTableEntry> pageTableEntries = pageTable.getPageTable();
		PageTableEntry[] array = pageTableEntries.toArray(new PageTableEntry[pageTableEntries.size()]);
		
		table.getItems().setAll(array);
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
	
	
	
	public int giveFrameNumberToFill(boolean eigen, PageTableEntry[] frameList){
		if(framesFreeAllocated.isEmpty()){
			return deleteFrameFromRam(eigen, frameList);
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
	
	public int deleteFrameFromRam(boolean eigen, PageTableEntry[] frameList){
		int LRU=Integer.MAX_VALUE;
		int number=-1;
		int index=-1;
		PageTableEntry pte=null;
		for(int i=0;i<framesTakenAllocated.size();i++){
			PageTableEntry help=frameList[framesTakenAllocated.get(i)];
			if(help.getLastAccessTime()<LRU){
				pte=help;
				LRU=help.getLastAccessTime();
				number=framesTakenAllocated.get(i);
				index=i;
				
			}
		}
		pte.setPresentBit(false);
		pte.setFrameNumber(-1);
		if(pte.isModifyBit()){
			pte.setRamToPersistent(pte.getRamToPersistent()+1);
			pte.setModifyBit(false);
		}	
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

	public TableView<PageTableEntry> getTable() {
		return table;
	}

	public void setTable(TableView<PageTableEntry> table) {
		this.table = table;
	}
	
	
	
	
}
