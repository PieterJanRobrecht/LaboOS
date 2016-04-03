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

		cardPanelXml2.setLayout(new BoxLayout(cardPanelXml2,1));

		//Grafieken maken
		ChartPanel chart = verwerker.maakGrafiekPanel();
		ChartPanel chartWait = verwerker.maakGrafiekWaitPanel();

		//Toevoegen aan de panel
		cardPanelXml2.add(chart);
		cardPanelXml2.add(chartWait);

		chartPanel.add("xml2",cardPanelXml2);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("\t 20 000 processen");
		System.out.println("-------------------------------------------------------------------------");
		cl.show(chartPanel, "xml2");
	}
}
