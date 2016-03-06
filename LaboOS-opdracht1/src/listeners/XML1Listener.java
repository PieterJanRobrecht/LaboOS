package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import data.Verwerker;

public class XML1Listener implements ActionListener{
	private Verwerker verwerker;
	
	public XML1Listener(Verwerker v){
		verwerker = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("\t 10 000 processen");
		System.out.println("---------------------------------------------------------------------------");
		verwerker.setFile(new File("processen10000.xml"));
	}
}
