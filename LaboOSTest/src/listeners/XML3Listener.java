package listeners;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import data.Verwerker;

public class XML3Listener implements ActionListener {
	private Verwerker verwerker;
	private CardLayout cl;
	private JPanel chartPanel;
	
	public XML3Listener(Verwerker v, CardLayout cl, JPanel chartPanel, JPanel cardPanelXml3){
		verwerker = v;
		verwerker.setFile(new File("processen50000.xml"));
		this.cl = cl;
		this.chartPanel = chartPanel;
		
		JPanel grafiekenCombo = new JPanel();
		grafiekenCombo.setLayout(new BoxLayout(grafiekenCombo,1));
		
		//Maken van grafiek met Nor Runtime
		ChartPanel chart = verwerker.maakGrafiekPanel();
		grafiekenCombo.add(chart);
				
		//Maken van grafiek met wait time
		ChartPanel chartWait = verwerker.maakGrafiekWaitPanel();
		grafiekenCombo.add(chartWait);
		
		cardPanelXml3.add(grafiekenCombo);
		chartPanel.add("xml3",cardPanelXml3);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("\t 50 000 processen");
		System.out.println("---------------------------------------------------------------------------");
		cl.show(chartPanel, "xml3");
	}
}
