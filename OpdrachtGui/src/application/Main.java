package application;
	
import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import controllers.Controller;
import data.InstructionList;
import data.Verwerker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
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

	        Controller controller = loader.<Controller>getController() ;
	        assert(controller != null);
	  		
	  		Verwerker verwerker=initVerwerker();
	        
	        controller.setVerwerker(verwerker);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Verwerker initVerwerker(){
		//OPGELET OS houdt 1 PT bij per proces
  		//Deze waarden zijn de machten. Bij de frame grootte = 2^12 dus we houden 12 bij.
  		int grootteFrame = 12;
  		//in aantal frames
  		int grootteRAM = 12;
  		int maxInRAM=4;
  		//Een proces bestaat uit max 16 pages en er zijn dus ook 16 entries per PT
  		int grootteVirtueel = 16;
  		int klok = 0;
  		
  		Verwerker verwerker=new Verwerker(grootteRAM,grootteFrame,grootteVirtueel,maxInRAM);
  		//Controller c = new Controller(verwerker);
  		return verwerker;
	}
}
