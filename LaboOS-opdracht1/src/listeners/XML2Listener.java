package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import data.Verwerker;

public class XML2Listener implements ActionListener {
	private Verwerker verwerker;
	
	public XML2Listener(Verwerker v){
		verwerker = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("\t 20 000 processen");
		System.out.println("---------------------------------------------------------------------------");
		verwerker.setFile(new File("processen20000.xml"));
	}
}
