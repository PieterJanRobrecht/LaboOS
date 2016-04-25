package model;

import java.util.*;

public class ProcessList {
	List<Process>processList=new ArrayList<Process>();
 
 	public List<Process> getProcessList() {
		return processList;
	}

	public void setProcessList(List<Process> processList) {
		this.processList = processList;
	}

	public ProcessList(){
 		
 	}
 	
 	public void addProcess(Process process){
 		processList.add(process.getPid(),process);
 	}
 	
 	public Process findProcess(int PID){
 		boolean found=false;
		int i=0;
		Process process=null;
		while(!found&&i<processList.size()){
			process=processList.get(i);
			if(process.getPid()==PID)found=true;
			else i++;
		}
		return process;
 	}
 	
 	public void deleteProcess(Process process){
 		processList.remove(process);
 	}
 	
 	public int getSize(){
 		return processList.size();
 	}

	public Process get(int i) {
		return processList.get(i);
	}
}
