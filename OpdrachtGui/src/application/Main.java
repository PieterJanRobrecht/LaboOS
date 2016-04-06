package application;
	
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import data.InstructionList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	public static void main(String[] args) {
		
		
		//OPGELET OS houdt 1 PT bij per proces
		//Deze waarden zijn de machten. Bij de frame grootte = 2^12 dus we houden 12 bij.
		int grootteFrame = 12;
		//in aantal frames
		int grootteRAM = 12;
		//Een proces bestaat uit max 16 pages en er zijn dus ook 16 entries per PT
		int grootteVirtueel = 16;
		int klok = 0;
		
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
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hello World!");
		VBox vbox = new VBox();
		Scene scene = new Scene(vbox,400,350);
        
		//Menu toevoegen
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("File");
		MenuItem file1 = new MenuItem("Instructions 30 3");
		MenuItem file2 = new MenuItem("Instructions 20000 4");
		MenuItem file3 = new MenuItem("Instructions 20000 20");
		
		menu.getItems().addAll(file1,file2,file3);
		menuBar.getMenus().addAll(menu);
		
		vbox.getChildren().add(menuBar);
		
		//Knop toevoegen
		Button stap = new Button();
		Button alles = new Button();
		stap.setText("Eén stap");
		alles.setText("Volledig bestand");
		stap.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Eén stap vooruit");
            }
        });
		alles.setOnAction(new EventHandler<ActionEvent>() {
			 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Volledig bestand doorlopen");
            }
        });
               
        vbox.getChildren().addAll(stap,alles);
        
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}
