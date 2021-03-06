package model;

import java.util.Observable;

public class Manager extends Observable{
	private int sizeRAM;
	private int sizePage;
	private int sizeVirtual;
	private int klok;
	private InstructionList instructionList;
	private ProcessList processList;
	private RAM ram;
	
	private PageTableEntry[] vorigeFrameList;
	
	public Manager(){
		
	}

	public Manager(int grootteRAM,int groottePage,int grootteVirtueel, int maxInRAM){
		this.klok=0;
		this.sizeRAM=grootteRAM;
		this.sizePage=groottePage;
		this.sizeVirtual=grootteVirtueel;
		processList = new ProcessList();
		ram=new RAM(grootteRAM,maxInRAM);
	}
	
	public void doNextInstruction(boolean single){
		vorigeFrameList = ram.getFrameList();
		Instruction instructie =instructionList.get(klok);
		switch(instructie.getOperation()){
			case "Start":doStart(instructie,klok);break;
			case "Read":doRead(instructie,klok);break;
			case "Write":doWrite(instructie,klok);break;
			case "Terminate":doTerminate(instructie,klok);break;
			default:System.out.println("Geen geldige instructie");break;
		}
		
		boolean singleInstruction = single;
		if(singleInstruction ){
			setChanged();
			notifyObservers();
		}
		klok++;
	}

	
	private void doStart(Instruction instructie,int klok) {
		Process process =new Process(instructie.getPid(),sizeVirtual);
		ram.addProcess(process);
		processList.addProcess(process);
		process.setLastAccesTime(klok);
	}

	private void doRead(Instruction instructie,int klok) {
		Process process=processList.findProcess(instructie.getPid());
		PageTableEntry pte=process.getPagetable().findPageTableEntry(instructie.getAddress()/Math.pow(2,sizePage));
		pte.setPid(process.getPid());
		if(!ram.inRAM(process)){
			ram.addProcess(process);
		}
		if(pte.isPresentBit()){
			pte.setLastAccessTime(klok);
			process.setLastAccesTime(klok);
		}
		else{
			ram.addFrame(process,pte);
			pte.setLastAccessTime(klok);
		}
		process.setLastAccesTime(klok);
	}

	private void doWrite(Instruction instructie,int klok) {
		Process process=processList.findProcess(instructie.getPid());
		PageTableEntry pte=process.getPagetable().findPageTableEntry(instructie.getAddress()/Math.pow(2,sizePage));
		pte.setPid(process.getPid());
//		System.out.println("PRocess id "+pte.getPid());
		if(!ram.inRAM(process)){
			ram.addProcess(process);
		}
		if(pte.isPresentBit()){
			pte.setLastAccessTime(klok);
		}
		else{
			ram.addFrame(process,pte);
			pte.setLastAccessTime(klok);
		}
		process.setLastAccesTime(klok);
		pte.setModifyBit(true);
		
	}

	private void doTerminate(Instruction instructie,int klok) {
		Process process=processList.findProcess(instructie.getPid());
		ram.deleteProcess(process);
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

	public int getKlok() {
		return klok;
	}

	public void setKlok(int klok) {
		this.klok = klok;
	}

	public void doAllInstructions() {
		for(int i=0;i<instructionList.getSize();i++){
			System.out.println(i);
			doNextInstruction(true);
		}
		setChanged();
		notifyObservers();
	}
	public int[] berekenWrites() {
		int toDisk = 0;
		int toRam = 0;
		for(model.Process p : processList.getProcessList()){
			for(PageTableEntry e : p.getPageTable().getPageTable()){
				toDisk+=e.getRamToPersistent();
				toRam+=e.getPersistentToRam();
			}
		}
		int[] resultaat = new int[] {toDisk,toRam};
		return resultaat;
	}

	public PageTableEntry[] getVorigeFrameList() {
		return vorigeFrameList;
	}

	public void setVorigeFrameList(PageTableEntry[] vorigeFrameList) {
		this.vorigeFrameList = vorigeFrameList;
	}
	
	
}
