package data;

import java.util.Observable;

public class Verwerker extends Observable{
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
			case "Start":doStart(instructie,klok);break;
			case "Read":doRead(instructie,klok);break;
			case "Wirte":doWrite(instructie,klok);break;
			case "Terminate":doTerminate(instructie,klok);break;
			default:System.out.println("Geen geldige instructie");break;
		}
	}

	
	private void doStart(Instruction instructie,int klok) {
		Process process =new Process(instructie.getPid(),grootteVirtueel);
		process.setLastAccesTime(klok);
		ram.addProcess(process);
		processList.addProcess(process);
	}

	private void doRead(Instruction instructie,int klok) {
		
		
	}

	private void doWrite(Instruction instructie,int klok) {
		// TODO Auto-generated method stub
		
	}

	private void doTerminate(Instruction instructie,int klok) {
		// TODO Auto-generated method stub
		
	}

	public InstructionList getInstructionList() {
		return instructionList;
	}

	public void setInstructionList(InstructionList instructionList) {
		this.instructionList = instructionList;
	}
	
	//Dummy methode om te kijken of MVC werkt zoals het hoort
	//Bij aanpassen van een waarde die belangrijk is voor de view moet je de twee laatste methodes oproepen
	public void test() {
		grootteRAM = 12;
		setChanged();
		notifyObservers();
	}

	public int getGrootteRAM() {
		return grootteRAM;
	}

	public void setGrootteRAM(int grootteRAM) {
		this.grootteRAM = grootteRAM;
	}

	public int getGroottePage() {
		return groottePage;
	}

	public void setGroottePage(int groottePage) {
		this.groottePage = groottePage;
	}

	public int getGrootteVirtueel() {
		return grootteVirtueel;
	}

	public void setGrootteVirtueel(int grootteVirtueel) {
		this.grootteVirtueel = grootteVirtueel;
	}

	public ProcessList getProcessList() {
		return processList;
	}

	public void setProcessList(ProcessList processList) {
		this.processList = processList;
	}

	public RAM getRam() {
		return ram;
	}

	public void setRam(RAM ram) {
		this.ram = ram;
	}
	
	
}
