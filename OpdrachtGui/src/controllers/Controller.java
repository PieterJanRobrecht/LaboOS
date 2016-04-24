package controllers;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import data.Instruction;
import data.InstructionList;
import data.PageTableEntry;
import data.ProcessList;
import data.RAM;
import data.Manager;
import data.PageTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class Controller implements Observer{

    @FXML
    private MenuItem file1;

    @FXML
    private MenuItem file2;

    @FXML
    private MenuItem file3;

    @FXML
    private Button eenStap;

    @FXML
    private Button volledig;
    
    @FXML
    private Button setKlok;

    @FXML
    private TextField timerField;
    
    @FXML
    private TextField procesField;

    @FXML
    private TextField instructieField;

    @FXML
    private TextField virtueelAdres;

    @FXML
    private TextField reeelAdres;

    @FXML
    private TextField frame;
    
    @FXML
    private TextField offset;
    
    @FXML
    private TextField writeToRam;

    @FXML
    private TextField writeToDisk;
    
    @FXML
    private TextField klokSetField;
    
    @FXML
    private TableView<PageTableEntry> ramTable;
    
    @FXML
    private TableColumn<PageTableEntry, Integer> ramFrame;

    @FXML
    private TableColumn<PageTableEntry, Integer> ramPage;

    @FXML
    private TableColumn<PageTableEntry, Integer> ramPid;
    
    @FXML
    private TableColumn<PageTableEntry, Integer> ramPageLast;

    @FXML
    private Label procesID;

    @FXML
    private TabPane tabPane;
    
    @FXML
    private TableView<PageTableEntry> pageTable;
    
    @FXML
    private TableColumn<PageTableEntry, Integer> pageNumber;
    
    @FXML
    private TableColumn<PageTableEntry, Boolean> pagePresent;

    @FXML
    private TableColumn<PageTableEntry, Boolean> pageModify;

    @FXML
    private TableColumn<PageTableEntry, Integer> pageLast;

    @FXML
    private TableColumn<PageTableEntry, Integer> pageFrame;
    
    private Manager manager;
    
    public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
    
    //Ik ga kijken om een goed MVC implementatie te maken zodanig dat het mogelijk is om alles mooi aan te passen als er iets
    //gebeurd zonder veel moeite te steken in al de rest
    //Verwerker = model -> moet setChanged();notifyObservers(); gebruiken bij wijzigen data
    //Controller = view -> moet update() implementeren
    
    @FXML
    void eenStapClicked(ActionEvent event) {
    	manager.doNextInstruction(true);
    }
    
    @FXML
    void volledigClicked(ActionEvent event) {
    	for(int i=manager.getKlok();i<manager.getInstructionList().getSize()-1;i++){
			manager.doNextInstruction(false);
		}
    	manager.doNextInstruction(true);
    }
    
    @FXML
    void setKlok(ActionEvent event) {
    	String klok = klokSetField.getText();
    	try{
    		int inhoud = Integer.parseInt(klok);
    		for(int i = manager.getKlok();i<inhoud;i++){
    			manager.doNextInstruction(true);
    		}
    	}catch(NumberFormatException e){
    		klokSetField.setText("Niet geldig");
    	}
    }

    @FXML
    void file1Clicked(ActionEvent event) {
    	File file = new File("Instructions_30_3.xml");

		JAXBContext jaxbContext;
		InstructionList lijst = null;
		try {
			jaxbContext = JAXBContext.newInstance(InstructionList.class);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			lijst = (InstructionList) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
//		System.out.println(lijst);
		manager.setInstructionList(lijst);
		resetView();
	}

    @FXML
    void file2Clicked(ActionEvent event) {
    	File file = new File("Instructions_20000_4.xml");

		JAXBContext jaxbContext;
		InstructionList lijst = null;
		try {
			jaxbContext = JAXBContext.newInstance(InstructionList.class);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			lijst = (InstructionList) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
//		System.out.println(lijst);
		manager.setInstructionList(lijst);
		resetView();
    }

    @FXML
    void file3Clicked(ActionEvent event) {
    	File file = new File("Instructions_20000_20.xml");

		JAXBContext jaxbContext;
		InstructionList lijst = null;
		try {
			jaxbContext = JAXBContext.newInstance(InstructionList.class);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			lijst = (InstructionList) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
//		System.out.println(lijst);
		manager.setInstructionList(lijst);
		resetView();
    }

	private void resetView() {
		resetWaardes();
		
		timerField.setText("");
		procesField.setText("");
		instructieField.setText("");
		virtueelAdres.setText("");
	    reeelAdres.setText("");
	    frame.setText("");
	    offset.setText("");
	    writeToRam.setText("");
	    writeToDisk.setText("");
	}

	private void resetWaardes() {
		manager.setKlok(0);
		manager.setProcessList(new ProcessList());
		
		ProcessList processen = manager.getProcessList();
		List<data.Process> lijst = processen.getProcessList();
		for(int i = 0;i<lijst.size();i++){
			data.Process p = lijst.get(i);
			for(int j = 0;j<p.getPageTable().getPageTable().size();j++){
				PageTableEntry pte = p.getPagetable().getPageTable().get(j);
				pte.setRamToPersistent(0);
				pte.setPersistentToRam(0);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		int klok = manager.getKlok();
		InstructionList lijst = manager.getInstructionList();
		Instruction instruction = lijst.get(klok);
		long virtAdress = instruction.getAddress();
		long pageSize = manager.getSizePage();
		int frame = convertVirtToReeel(manager,klok);
		int offsetField = (int) (virtAdress%Math.pow(2, pageSize));
		
		timerField.setText(klok+1+"");
		procesField.setText(instruction.getPid()+"");
		instructieField.setText(instruction.getOperation());
		virtueelAdres.setText(lijst.get(klok).getAddress()+"");
		int reeel = offsetField;
		if(frame!=-1){
			reeel += (int) (frame*Math.pow(2, manager.getSizePage()));
		}
		this.frame.setText(frame+"");
		reeelAdres.setText(reeel+"");
		offset.setText(offsetField+"");
		procesID.setText(instruction.getPid()+"");
		
		updateRAMTable();
		updatePageTable();
		
		int[] waardes = manager.berekenWrites();
		writeToDisk.setText(waardes[0]+"");
		writeToRam.setText(waardes[1]+"");
	}

	private void updatePageTable() {
		int klok = manager.getKlok();
		InstructionList lijst = manager.getInstructionList();
		Instruction instruction = lijst.get(klok);
		
		int pid = instruction.getPid();
		data.Process process = manager.getProcessList().findProcess(pid);
		tabPane.getTabs().clear();
		for(int i =0;i<manager.getProcessList().getSize();i++){
			data.Process proces = manager.getProcessList().get(i);
			
			TableView<PageTableEntry> table = proces.getTable();
						
			List<PageTableEntry> pageTableEntries = proces.getPageTable().getPageTable();
			PageTableEntry[] array = pageTableEntries.toArray(new PageTableEntry[pageTableEntries.size()]);
			
			table.getItems().setAll(array);
			
			Tab t = new Tab();
			t.setText("Proces "+proces.getPid()+"");
			t.setContent(table);
			tabPane.getTabs().add(t);
		}
	}

	private void updateRAMTable() {
		RAM ram = manager.getRam();
		PageTableEntry[] ramArray = ram.getFrameList();
		this.ramTable.getItems().setAll(ramArray);
	}

	private int convertVirtToReeel(Manager manager,int klok) {
		InstructionList lijst = manager.getInstructionList();
		Instruction instruction = lijst.get(klok);
		
		System.out.println(instruction);
		
		long virtAdress = instruction.getAddress();
		long pageSize = manager.getSizePage();
		double pageEntry = virtAdress/Math.pow(2, pageSize);
		
//		System.out.println(pageEntry);
		
		int pid = instruction.getPid();
		data.Process process = manager.getProcessList().findProcess(pid);
		
//		System.out.println(new Double(pageEntry).intValue());
		
		PageTableEntry pageTableEntry = process.getPagetable().get(new Double(pageEntry).intValue());
		
		System.out.println(pageTableEntry);
		
		int frame = pageTableEntry.getFrameNumber();
		System.out.println(frame);
		return frame;
	}
	
	@FXML
	public void initialize(){
		//gebruikt voor de init van de view
//		pageNumber.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("pageNumber"));
//		pageModify.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Boolean>("modifyBit"));
//		pagePresent.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Boolean>("presentBit"));
//		pageLast.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("lastAccessTime"));
//		pageFrame.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("frameNumber"));
//		
		ramFrame.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("frameNumber"));
		ramPage.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("pageNumber"));
		ramPageLast.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("lastAccessTime"));
		ramPid.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("pid"));
		
	}
	

}
