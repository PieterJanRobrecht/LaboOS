package data;

import java.util.ArrayList;
import java.util.List;

public class RAM {
	private int grootteRAM;
	private int maxInRAM;
	private Process[] frameList;
	private ProcessList processInRAM=new ProcessList();
	
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
	
	public void addFrame(Page page){
		
	}
	
	public void swapFrame(){
		
	}
	
	public void swapProcess(Process process){
		
	}
	
	public void deleteProcess(){
		
	}
	
}
