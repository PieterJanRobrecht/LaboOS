package data;

import java.util.Observable;

public class Manager extends Observable{
	private int sizeRAM;
	private int sizePage;
	private int sizeVirtual;
	private int klok;
	private InstructionList instructionList;
	private ProcessList processList;
	private RAM ram;
	
	public Manager(){
		
	}
	
	public Manager(int grootteRAM,int groottePage,int grootteVirtueel, int maxInRAM){
		this.klok=0;
		this.sizeRAM=grootteRAM;
		this.sizePage=groottePage;
		this.sizeVirtual=grootteVirtueel;
		ram=new RAM(grootteRAM,maxInRAM);
	}
	
	public void doNextInstruction(){
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
		Process process =new Process(instructie.getPid(),sizeVirtual);
		process.setLastAccesTime(klok);
		ram.addProcess(process);
		processList.addProcess(process);
	}

	private void doRead(Instruction instructie,int klok) {
		Process process=processList.findProcess(instructie.getPid());
		process.pageTable.findPageTableEntry(instructie.getAddress()/Math.pow(2,sizePage));
		
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
		sizeRAM = 12;
		setChanged();
		notifyObservers();
	}

	public int getSizeRAM() {
		return sizeRAM;
	}

	public int getSizePage() {
		return sizePage;
	}

	public int getSizeVirtual() {
		return sizeVirtual;
	}

	public ProcessList getProcessList() {
		return processList;
	}

	public RAM getRam() {
		return ram;
	}

	public void setSizeRAM(int sizeRAM) {
		this.sizeRAM = sizeRAM;
	}

	public void setSizePage(int sizePage) {
		this.sizePage = sizePage;
	}

	public void setSizeVirtual(int sizeVirtual) {
		this.sizeVirtual = sizeVirtual;
	}

	public void setProcessList(ProcessList processList) {
		this.processList = processList;
	}

	public void setRam(RAM ram) {
		this.ram = ram;
	}

	public Process getProcess(int pid){
		Process process = null;
		for(int i = 0;i<processList.getSize();i++){
			Process p = processList.get(i);
			if(p.getPid()==pid){
				process = p;
			}
		}
		return process;
	}

	public int getKlok() {
		return klok;
	}

	public void setKlok(int klok) {
		this.klok = klok;
	}
	
	
}
