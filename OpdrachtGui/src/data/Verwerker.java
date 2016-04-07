package data;

public class Verwerker {
	private int grootteRAM;
	private int groottePage;
	private int grootteVirtueel;
	private InstructionList instructionList;
	private ProcessList processList;
	private RAM ram;
	
	public Verwerker(){
		
	}
	
	public Verwerker(int grootteRAM,int groottePage,int grootteVirtueel, int maxInRAM){
		this.grootteRAM=grootteRAM;
		this.groottePage=groottePage;
		this.grootteVirtueel=grootteVirtueel;
		ram=new RAM(grootteRAM,maxInRAM);
	}
	
	public void doNextInstruction(int klok, ProcessList processlist){
		Instruction instructie =instructionList.get(klok);
		switch(instructie.getOperation()){
			case "Start":doStart(instructie);break;
			case "Read":doRead(instructie);break;
			case "Wirte":doWrite(instructie);break;
			case "Terminate":doTerminate(instructie);break;
			default:System.out.println("Geen geldige instructie");break;
		}
	}

	
	private void doStart(Instruction instructie) {
		Process process =new Process(instructie.getPid(),grootteVirtueel);
		ram.addProcess(process);
		processList.addProcess(process);
	}

	private void doRead(Instruction instructie) {
		
		
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
