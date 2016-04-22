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
			int framesToGivePerProcess=0;
			switch(processInRAM.processList.size()){
				case'0':framesToGivePerProcess=12;break;
				case'1':framesToGivePerProcess=6;break;
				case'2':framesToGivePerProcess=2;break;
				case'3':framesToGivePerProcess=1;break;
				default:System.out.println("ERRRRRR");
			}
			for(Process p:processInRAM.processList){
				for(int i=0;i<framesToGivePerProcess;i++){
					
				}
			}
		}
	}
	
	public void addFrame(Process process, PageTableEntry pte){
		int frameNumber=process.giveFrameNumberToFill();
		pte.setPresentBit(true);
		frameList[frameNumber]=pte;
	}
	
	public void swapProcess(Process process){
		for(Process p:processInRAM.processList){
			
		}
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
