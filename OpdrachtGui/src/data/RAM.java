package data;

import java.util.ArrayList;
import java.util.List;

public class RAM {
	private int grootteRAM;
	private int maxInRAM;
	private PageTableEntry[] frameList;
	private ProcessList processInRAM=new ProcessList();

	
	//TODO toevoegen in framelist:
	//welk pagenummer er momenteel in het frame zit
	
	public RAM(int grootteRAM, int maxInRAM){
		this.grootteRAM=grootteRAM;
		this.maxInRAM=maxInRAM;
		setFrameList(new PageTableEntry[grootteRAM]);
	}
	
	public PageTableEntry[] getFrameList() {
		return frameList;
	}

	public void setFrameList(PageTableEntry[] frameList) {
		this.frameList = frameList;
	}

	public void addProcess(Process process){
		if(processInRAM.getSize()==maxInRAM){
			swapProcess(process);
		}
		else{
			for(Process p:processInRAM.processList){
				
			}
		}
	}
	
	public void addFrame(Process process, PageTableEntry pte){
		int frameNumber=process.giveFrameNumberToFill();
		frameList[frameNumber]=pte;
	}
	
	public void deleteFrame(PageTableEntry pte){
		
	}
	
	public void swapFrame(){
		
	}
	
	
	public void swapProcess(Process process){
		
	}
	
	public void deleteProcess(){
		
	}
	
	public boolean inRAM(Process process){
		if(processInRAM.processList.contains(process)){
			return true;
		}
		else return false;
	}
	
}
