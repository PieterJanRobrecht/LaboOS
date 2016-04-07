package application;
	
import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import data.InstructionList;
import data.Verwerker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static void main(String[] args) {
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
		File file = new File("Instructions_30_3.xml");

		JAXBContext jaxbContext;
		InstructionList lijst = null;
		try {
			jaxbContext = JAXBContext.newInstance(InstructionList.class);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			lijst = (InstructionList) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(lijst);
		verwerker.setInstructionList(lijst);
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Practicum Opdracht 2");
		
		Parent page=null;
		try {
			page = FXMLLoader.load(getClass().getClassLoader().getResource("Sample.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        Scene scene = new Scene(page);
        
        primaryStage.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}
