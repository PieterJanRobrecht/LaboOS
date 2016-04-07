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
			
		}
		else{
			
		}
	}
	
	public void swapFrame(){
		
	}
	
	public void swapProcess(){
		
	}
	
	public void deleteProcess(){
		
	}
	
}
