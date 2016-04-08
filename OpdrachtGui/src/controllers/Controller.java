package controllers;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import data.InstructionList;
import data.PageTableEntry;
import data.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private TableView<?> ramTable;
    
    @FXML
    private TableColumn<?, String> ramFrame;

    @FXML
    private TableColumn<?, String> ramPage;

    @FXML
    private TableColumn<?, String> ramPid;

    @FXML
    private Text procesID;

    @FXML
    private TableView<PageTableEntry> pageTable;
    
    @FXML
    private TableColumn<PageTableEntry, Boolean> pagePresent;

    @FXML
    private TableColumn<PageTableEntry, Boolean> pageModify;

    @FXML
    private TableColumn<PageTableEntry, Integer> pageLast;

    @FXML
    private TableColumn<PageTableEntry, Integer> pageFrame;
    
    private Manager verwerker;
    
    public Manager getVerwerker() {
		return verwerker;
	}

	public void setVerwerker(Manager verwerker) {
		this.verwerker = verwerker;
	}
    
    //Ik ga kijken om een goed MVC implementatie te maken zodanig dat het mogelijk is om alles mooi aan te passen als er iets
    //gebeurd zonder veel moeite te steken in al de rest
    //Verwerker = model -> moet setChanged();notifyObservers(); gebruiken bij wijzigen data
    //Controller = view -> moet update() implementeren
    
    @FXML
    void eenStapClicked(ActionEvent event) {
    	//TODO misschien wel handig om methode eenStap in verwerker te hebben
    	//verwerker.stap();
    	verwerker.test();
    }
    
    @FXML
    void volledigClicked(ActionEvent event) {
    	//TODO misschien handig om dan methode alles doorlopen te hebben
    	//verwerker.volledig();
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
		System.out.println(lijst);
		verwerker.setInstructionList(lijst);
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
		System.out.println(lijst);
		verwerker.setInstructionList(lijst);
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
		System.out.println(lijst);
		verwerker.setInstructionList(lijst);
    }

	@Override
	public void update(Observable o, Object arg) {
		timerField.setText(verwerker.getGrootteRAM()+"");
		
	}
	
	ObservableList<PageTableEntry> data = FXCollections.observableArrayList(
		    new PageTableEntry(true, false,1,15),
		    new PageTableEntry(true, true,5,10)
		);
	
	@FXML
	public void initialize(){
		//gebruikt voor de init van de view
		pageModify.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Boolean>("modifyBit"));
		pagePresent.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Boolean>("presentBit"));
		pageLast.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("lastAccessTime"));
		pageFrame.setCellValueFactory(new PropertyValueFactory<PageTableEntry,Integer>("frameNumber"));
		pageTable.getItems().setAll(this.data);
	}
	

}
