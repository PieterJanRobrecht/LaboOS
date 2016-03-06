package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import data.Verwerker;

public class XML3Listener implements ActionListener {
	private Verwerker verwerker;
	
	public XML3Listener(Verwerker v){
		verwerker = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("\t 50 000 processen");
		System.out.println("---------------------------------------------------------------------------");
		verwerker.setFile(new File("processen50000.xml"));
	}
}
