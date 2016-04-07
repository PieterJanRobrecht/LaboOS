package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
	
	int i = 0;

	private Button eenStap;

	public void eenStapClicked(){
		System.out.println(i++);
	}
}
