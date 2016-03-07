package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import data.GlobalVar;
import data.GlobalVarList;
import data.Verwerker;
import listeners.XML1Listener;
import listeners.XML2Listener;
import listeners.XML3Listener;

public class Gui extends JFrame {
	private JFrame mijnFrame;
	private JPanel panel;
	private JPanel cardPanel;
	private JPanel chartPanel;
	private JTextArea systemArea;
	private ChartPanel chart;
	private ChartPanel chartWait;
	private JPanel knoppenPanel;
	private JScrollPane systemScroll;
	private JButton xml1;
	private JButton xml2;
	private JButton xml3;
	private Verwerker verwerker;

	public Gui() {
		//Verwerker maken
		verwerker = new Verwerker();
		
		//panel waar alle gui elementen in moeten komen
		mijnFrame = new JFrame();
		mijnFrame.setTitle("Gui");
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		//Text gebied voor de syso
		systemArea = new JTextArea(30,20);
		systemArea.setEditable(false);
		
		//Maken van de systemScroll
		systemScroll = new JScrollPane(systemArea);
		
		 MessageConsole console = new MessageConsole(systemArea);
		 console.redirectOut();
	     console.redirectErr(Color.RED, null);
		
		//Init van chartPanel waar alle grafieken in gaan komen
		chartPanel = new JPanel();
		//chartPanel.setLayout(new BoxLayout(chartPanel,1) );
				
		//Init van cardPanel waar alles zich gedraagd als een kaartenstapel
		CardLayout cl = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cl);
		
		//KnoppenPanel maken
		knoppenPanel = new JPanel();
		knoppenPanel.setLayout(new BoxLayout(knoppenPanel,1));
		xml1 = new JButton("Uitvoeren 10 000 processen");
		xml1.addActionListener(new XML1Listener(verwerker,cl,cardPanel));
		xml2 = new JButton("Uitvoeren 20 000 processen");
		xml2.addActionListener(new XML2Listener(verwerker,cl,cardPanel));
		xml3 = new JButton("Uitvoeren 50 000 processen");
		xml3.addActionListener(new XML3Listener(verwerker,cl,cardPanel));
		knoppenPanel.add(xml1);
		knoppenPanel.add(xml2);
		knoppenPanel.add(xml3);
		knoppenPanel.add(systemScroll);
		panel.add(knoppenPanel, BorderLayout.WEST);
		
		//Toevoegen cardPanel aan chartPanel
		chartPanel.add(cardPanel);
		
		//Toevoegen van grafieken aan panel
		panel.add(chartPanel,BorderLayout.CENTER);
		
		//Alles samen steken in 1 frame
		mijnFrame.setLayout(new BorderLayout());
		mijnFrame.add(panel,BorderLayout.CENTER);
		mijnFrame.pack();
		mijnFrame.setVisible(true);
		mijnFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


