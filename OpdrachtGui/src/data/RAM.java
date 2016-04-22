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
				case 0 :framesToGivePerProcess=12;break;
				case 1 :framesToGivePerProcess=6;break;
				case 2 :framesToGivePerProcess=2;break;
				case 3 :framesToGivePerProcess=1;break;
				default:System.out.println("ERRRRRR");
			}
			if(processInRAM.getSize()==0){
				for(int i=0;i<framesToGivePerProcess;i++){
					process.getFramesFreeAllocated().add(i);
				}
			}
			else{
				for(Process p:processInRAM.processList){
					for(int i=0;i<framesToGivePerProcess;i++){
						process.getFramesFreeAllocated().add(p.giveFrameNumberToFill(false));
					}
				}
			}
			processInRAM.processList.add(process);
		}
	}
	
	public void addFrame(Process process, PageTableEntry pte){
		int frameNumber=process.giveFrameNumberToFill(true);
		pte.setPresentBit(true);
		pte.setFrameNumber(frameNumber);
		frameList[frameNumber]=pte;
	}
	
	public void swapProcess(Process process){
		int timer=Integer.MAX_VALUE;
		Process oudst=null;
		for(Process p:processInRAM.processList){
			if(p.getLastAccesTime()<timer){
				timer=p.getLastAccesTime();
				oudst=p;
			}
		}
		
		deleteProcess(process);
	}
	
	public void deleteProcess(Process process){
		process.getFramesFreeAllocated().clear();
		process.getFramesTakenAllocated().clear();
		for(PageTableEntry pte: process.getPageTable().getPageTable()){
			pte.setRamToPersistent(pte.getPersistentToRam()+1);
			pte.setModifyBit(false);
			pte.setPresentBit(false);
		}
	}
	
	public boolean inRAM(Process process){
		if(processInRAM.processList.contains(process)){
			return true;
		}
		else return false;
	}
	
}
