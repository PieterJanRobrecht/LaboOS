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
    private TextField timerField;

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
    	for(int i=manager.getKlok();i<manager.getInstructionList().getSize();i++){
			manager.doNextInstruction(true);
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
		manager.setKlok(0);
		timerField.setText("");
		instructieField.setText("");
		virtueelAdres.setText("");
	    reeelAdres.setText("");
	    frame.setText("");
	    offset.setText("");
	    writeToRam.setText("");
	    writeToDisk.setText("");
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
		instructieField.setText(instruction.getOperation());
		virtueelAdres.setText(lijst.get(klok).getAddress()+"");
		int reeel =(int) (frame*Math.pow(2, manager.getSizePage()))+offsetField;
		reeelAdres.setText(reeel+"");
		this.frame.setText(frame+"");
		offset.setText(offsetField+"");
		procesID.setText(instruction.getPid()+"");
		
		updateRAMTable();
		updatePageTable();
		
		int[] waardes = manager.berekenWrites();
		writeToDisk.setText(waardes[0]+"");
		writeToRam.setText(waardes[1]+"");
	}

	private void updatePageTable() {
		//TODO pagenummer toevoegen
		int klok = manager.getKlok();
		InstructionList lijst = manager.getInstructionList();
		Instruction instruction = lijst.get(klok);
		
		int pid = instruction.getPid();
		data.Process process = manager.getProcessList().findProcess(pid);
		PageTable pageTable = process.getPagetable();
		List<PageTableEntry> pageTableEntries = pageTable.getPageTable();
		PageTableEntry[] array = pageTableEntries.toArray(new PageTableEntry[pageTableEntries.size()]);
		this.pageTable.getItems().setAll(array);
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
		pageNumber.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("pageNumber"));
		pageModify.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Boolean>("modifyBit"));
		pagePresent.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Boolean>("presentBit"));
		pageLast.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("lastAccessTime"));
		pageFrame.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("frameNumber"));
		
		ramFrame.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("frameNumber"));
		ramPage.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("pageNumber"));
		ramPageLast.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("lastAccessTime"));
		ramPid.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("pid"));
	}
	

}
