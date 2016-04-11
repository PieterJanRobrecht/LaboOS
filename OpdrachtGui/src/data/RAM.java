package data;

import java.util.ArrayList;
import java.util.List;

public class RAM {
	private int grootteRAM;
	private int maxInRAM;
	private Process[] frameList;
	private ProcessList processInRAM=new ProcessList();
	
	//TODO toevoegen in framelist:
		//welk pagenummer er momenteel in het frame zit
	
	public RAM(int grootteRAM, int maxInRAM){
		this.grootteRAM=grootteRAM;
		this.maxInRAM=maxInRAM;
		frameList=new Process[grootteRAM];
	}
	
	public void addProcess(Process process){
		if(processInRAM.getSize()==maxInRAM){
			swapProcess(process);
		}
		else{
			
		}
	}
	
	public void addFrame(){
		
	}
	
	public void swapFrame(){
		
	}
	
	public void swapProcess(Process process){
		
	}
	
	public void deleteProcess(){
		
	}
	
}
