package data;

import java.util.*;

public class ProcessList {
 List<Process>processList=new ArrayList<Process>();
 
 	public ProcessList(){
 		
 	}
 	
 	public void addProcess(Process process){
 		processList.add(process.getPid(),process);
 	}
 	
 	public Process findProcess(int PID){
 		boolean found=false;
		int i=0;
		Process process=null;
		while(!found){
			process=processList.get(i);
			if(process.pid==PID)found=true;
			else i++;
		}
		return process;
 	}
 	
 	public int getSize(){
 		return processList.size();
 	}
}
