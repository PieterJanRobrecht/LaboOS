package data;

import java.util.*;

public class ProcessList {
 List<Process>processenLijst=new ArrayList<Process>();
 
 	public ProcessList(){
 		
 	}
 	
 	public void addProcess(Process process){
 		processenLijst.add(process);
 	}
 	
 	public Process findProcess(int PID){
 		boolean gevonden=false;
		int i=0;
		Process process=null;
		while(!gevonden){
			process=processenLijst.get(i);
			if(process.pid==PID)gevonden=true;
			else i++;
		}
		return process;
 	}
 	
 	public int getSize(){
 		return processenLijst.size();
 	}
}
