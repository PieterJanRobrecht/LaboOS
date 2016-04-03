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

		cardPanelXml3.setLayout(new BoxLayout(cardPanelXml3,1));

		//Grafieken maken
		ChartPanel chart = verwerker.maakGrafiekPanel();
		ChartPanel chartWait = verwerker.maakGrafiekWaitPanel();

		//Toevoegen aan de panel
		cardPanelXml3.add(chart);
		cardPanelXml3.add(chartWait);

		chartPanel.add("xml3",cardPanelXml3);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("\t 50 000 processen");
		System.out.println("-------------------------------------------------------------------------");
		cl.show(chartPanel, "xml3");
	}
}
