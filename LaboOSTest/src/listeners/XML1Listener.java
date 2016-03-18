package listeners;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.Border;

import org.jfree.chart.ChartPanel;

import data.Verwerker;

public class XML1Listener implements ActionListener{
	private Verwerker verwerker;
	private CardLayout cl;
	private JPanel chartPanel;
	
	public XML1Listener(Verwerker v, CardLayout cl, JPanel chartPanel, JPanel cardPanelXml1){
		verwerker = v;
		verwerker.setFile(new File("processen10000.xml"));
		this.cl = cl;
		this.chartPanel = chartPanel;

		cardPanelXml1.setLayout(new BoxLayout(cardPanelXml1,1));

		//Grafieken maken
		ChartPanel chart = verwerker.maakGrafiekPanel();
		ChartPanel chartWait = verwerker.maakGrafiekWaitPanel();

		//Toevoegen aan de panel
		cardPanelXml1.add(chart);
		cardPanelXml1.add(chartWait);

		chartPanel.add("xml1",cardPanelXml1);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("\t 10 000 processen");
		System.out.println("-------------------------------------------------------------------------");
		cl.show(chartPanel, "xml1");
	}
}
