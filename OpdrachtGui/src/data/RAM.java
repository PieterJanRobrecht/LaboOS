package data;

import java.util.List;

public class RAM {
	private int grootte;
	private Process[] frameList;
	
	public RAM(int grootte){
		this.grootte=grootte;
		frameList=new Process[grootte];
	}
	
	
}
