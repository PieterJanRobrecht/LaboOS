package listeners;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import data.Verwerker;

public class XML2Listener implements ActionListener {
	private Verwerker verwerker;
	private CardLayout cl;
	private JPanel chartPanel;
	
	public XML2Listener(Verwerker v, CardLayout cl, JPanel chartPanel, JPanel cardPanelXml2){
		verwerker = v;
		verwerker.setFile(new File("processen20000.xml"));
		this.cl = cl;
		this.chartPanel = chartPanel;
		
		JPanel grafiekenCombo = new JPanel();
		grafiekenCombo.setLayout(new BoxLayout(grafiekenCombo,1));
		
		//Maken van grafiek met Nor Runtime
		ChartPanel chart = verwerker.maakGrafiek();
		grafiekenCombo.add(chart);
				
		//Maken van grafiek met wait time
		ChartPanel chartWait = verwerker.maakGrafiekWait();
		grafiekenCombo.add(chartWait);
		
//		grafiekenCombo.add(chart);
//		grafiekenCombo.add(chartWait);
		grafiekenCombo.setVisible(true);
		
		cardPanelXml2.add(grafiekenCombo);
		chartPanel.add(cardPanelXml2, "xml2");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("\t 20 000 processen");
		System.out.println("---------------------------------------------------------------------------");
		cl.show(chartPanel, "xml2");
	}
}
