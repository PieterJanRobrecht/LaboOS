package listeners;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import data.Verwerker;

public class XML1Listener implements ActionListener{
	private Verwerker verwerker;
	private CardLayout cl;
	private JPanel cardPanel;
	
	public XML1Listener(Verwerker v, CardLayout cl, JPanel cardPanel){
		verwerker = v;
		verwerker.setFile(new File("processen10000.xml"));
		this.cl = cl;
		this.cardPanel = cardPanel;
		
		//Maken van grafiek met Nor Runtime
		ChartPanel chart = verwerker.maakGrafiek();
		cardPanel.add(chart);
				
		//Maken van grafiek met wait time
		ChartPanel chartWait = verwerker.maakGrafiekWait();
		cardPanel.add(chartWait);
		
		JPanel grafiekenCombo = new JPanel();
		grafiekenCombo.setLayout(new BoxLayout(grafiekenCombo,1));
		
		grafiekenCombo.add(chart);
		grafiekenCombo.add(chartWait);
		
		cardPanel.add(grafiekenCombo, "xml1");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("\t 10 000 processen");
		System.out.println("---------------------------------------------------------------------------");
		cl.show(cardPanel, "xml1");
	}
}
