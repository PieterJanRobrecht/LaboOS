package application;
	
import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.InstructionList;
import model.Manager;
import view.View;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
	        Parent root = (Parent) loader.load(getClass().getClassLoader().getResource("Sample.fxml").openStream());

	        primaryStage.setTitle("Practicum Opdracht 2");
	        primaryStage.setScene(new Scene(root));
	        primaryStage.show();

	        View controller = loader.<View>getController() ;
	        assert(controller != null);
	  		
	  		Manager manager=initManager();
	        
	  		//link tussen model en view
	  		controller.setManager(manager);
	        manager.addObserver(controller);
	        setFirstFile(manager);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setFirstFile(Manager manager) {
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
//		manager.doNextInstruction(false);
	}

	public Manager initManager(){
		//OPGELET OS houdt 1 PT bij per proces
  		//Deze waarden zijn de machten. Bij de frame grootte = 2^12 dus we houden 12 bij.
  		int sizeFrame = 12;
  		//in aantal frames
  		int sizeRAM = 12;
  		int maxInRAM=4;
  		//Een proces bestaat uit max 16 pages en er zijn dus ook 16 entries per PT
  		int sizeVirtual = 16;
  		int klok = 0;
  		
  		Manager verwerker=new Manager(sizeRAM,sizeFrame,sizeVirtual,maxInRAM);
  		//Controller c = new Controller(verwerker);
  		return verwerker;
	}
}
