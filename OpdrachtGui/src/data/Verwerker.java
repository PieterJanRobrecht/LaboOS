package data;

public class Verwerker {
	private InstructionList instructionList;
	private RAM ram;
	
	public Verwerker(){
		
	}
	
	public void doNextInstruction(int klok, ProcessList processlist){
		Instruction instructie =instructionList.get(klok);
		switch(instructie.getOperation()){
			case "Start":doStart(instructie);break;
			case "Read":doRead(instructie);break;
			case "Wirte":doWrite(instructie);break;
			case "Terminate":doTerminate(instructie);break;
		}
	}

	
	private void doStart(Instruction instructie) {
		Process process =new Process(instructie.getPid());
		
		
	}

	private void doRead(Instruction instructie) {
		// TODO Auto-generated method stub
		
	}

	private void doWrite(Instruction instructie) {
		// TODO Auto-generated method stub
		
	}

	private void doTerminate(Instruction instructie) {
		// TODO Auto-generated method stub
		
	}

	public InstructionList getInstructionList() {
		return instructionList;
	}

	public void setInstructionList(InstructionList instructionList) {
		this.instructionList = instructionList;
	}
	
	
}
