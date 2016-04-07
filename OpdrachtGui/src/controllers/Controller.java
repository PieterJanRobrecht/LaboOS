package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
	
	int i = 0;
	
	@FXML
	private Button eenStap;

	public void eenStapClicked(){
		System.out.println(i++);
	}

	public Button getEenStap() {
		return eenStap;
	}

	public void setEenStap(Button eenStap) {
		this.eenStap = eenStap;
	}
}
