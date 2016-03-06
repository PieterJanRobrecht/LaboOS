package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
		
		//KnoppenPanel maken
		knoppenPanel = new JPanel();
		knoppenPanel.setLayout(new BoxLayout(knoppenPanel,1));
		xml1 = new JButton("Uitvoeren 10 000 processen");
		xml1.addActionListener(new XML1Listener(verwerker));
		xml2 = new JButton("Uitvoeren 20 000 processen");
		xml2.addActionListener(new XML2Listener(verwerker));
		xml3 = new JButton("Uitvoeren 50 000 processen");
		xml3.addActionListener(new XML3Listener(verwerker));
		knoppenPanel.add(xml1);
		knoppenPanel.add(xml2);
		knoppenPanel.add(xml3);
		knoppenPanel.add(systemScroll);
		panel.add(knoppenPanel, BorderLayout.WEST);
		
//		//Maken van grafiek met Nor Runtime
//		chart = maakChartPanel(verwerker.getGegevensAlleAlgo());
//		chartPanel = new JPanel();
//		chartPanel.setLayout(new BoxLayout(chartPanel,1) );
//		chartPanel.add(chart);
//		
//		//Maken van grafiek met wait time
//		chartWait = maakChartPanelWait(verwerker.getGegevensAlleAlgo());
//		chartPanel.add(chartWait);
//		
//		//Toevoegen van grafieken aan panel
//		panel.add(chartPanel,BorderLayout.EAST);
		
		//Alles samen steken in 1 frame
		mijnFrame.setLayout(new BorderLayout());
		mijnFrame.add(panel,BorderLayout.CENTER);
		mijnFrame.pack();
		mijnFrame.setVisible(true);
		mijnFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private ChartPanel maakChartPanelWait(List<GlobalVarList> gegevensAlleAlgoritmen) {
		JFreeChart lineChart = ChartFactory.createXYLineChart("Scheduling Algortimes", "Gemiddelde ServiceTime", "Gemiddelde WaitTime", createDatasetGrafiekWait(gegevensAlleAlgoritmen),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		
		final XYPlot plot = lineChart.getXYPlot( );
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	    //Kleuren aanpassen voor alle grafieken 
		//Momenteel is er maar 1 aanwezig dus de rest maakt niet echt uit
		renderer.setSeriesPaint( 0 , Color.RED );
	    renderer.setSeriesPaint( 1 , Color.GREEN );
	    renderer.setSeriesPaint( 2 , Color.YELLOW );
	    final NumberAxis domainAxis = new LogarithmicAxis("Gemiddelde ServiceTime");
//	    final NumberAxis domainAxis = new NumberAxis("x");
        final NumberAxis rangeAxis = new LogarithmicAxis("Gemiddelde WaitTime");
//      final NumberAxis rangeAxis = new NumberAxis("y");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);
        plot.setRenderer( renderer );
		return chartPanel;
	}

	private ChartPanel maakChartPanel(List<GlobalVarList> gegevensAlleAlgoritmen) {
		JFreeChart lineChart = ChartFactory.createXYLineChart("Scheduling Algortimes", "Gemiddelde ServiceTime", "Normalised TAT", createDataset(gegevensAlleAlgoritmen),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		
		final XYPlot plot = lineChart.getXYPlot( );
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	    //Kleuren aanpassen voor alle grafieken 
		//Momenteel is er maar 1 aanwezig dus de rest maakt niet echt uit
		renderer.setSeriesPaint( 0 , Color.RED );
	    renderer.setSeriesPaint( 1 , Color.GREEN );
	    renderer.setSeriesPaint( 2 , Color.YELLOW );
	    final NumberAxis domainAxis = new LogarithmicAxis("Gemiddelde ServiceTime");
//	    final NumberAxis domainAxis = new NumberAxis("x");
        final NumberAxis rangeAxis = new LogarithmicAxis("Normalised TAT");
//      final NumberAxis rangeAxis = new NumberAxis("y");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);
        plot.setRenderer( renderer );
		return chartPanel;
	}
	
	private XYDataset createDataset(List<GlobalVarList> gegevensAlleAlgoritmen) {
		//Lijst met alle gegevens van alle algoritmen worden aangeboden
		//Moeten dus verschillende lijnen maken voor alle objecten in de lijst apart
		//Miss best doen in een dubbele for één voor de lijst te doorlopen en de tweede voor data te adden
		XYSeriesCollection  dataset = new XYSeriesCollection ();
		for(int i=0;i<gegevensAlleAlgoritmen.size();i++){
			//één dataset van alle algoritmes is geselecteerd
			GlobalVarList data = gegevensAlleAlgoritmen.get(i);
			XYSeries hulp = new XYSeries( data.getAlgoritmeNaam() );
			for(int j = 0;j<data.getSize();j++){
				GlobalVar percentiel = data.getPercentiel(j);
				//System.out.println(percentiel.getAverageServiceTime()+" "+percentiel.getAverageNorRuntime());
				hulp.add(percentiel.getAverageServiceTime(),percentiel.getAverageNorRuntime());
			}
			dataset.addSeries(hulp);
		}
		return dataset;
	}
	
	private XYDataset createDatasetGrafiekWait(List<GlobalVarList> gegevensAlleAlgoritmen){
		//Lijst met alle gegevens van alle algoritmen worden aangeboden
				//Moeten dus verschillende lijnen maken voor alle objecten in de lijst apart
				//Miss best doen in een dubbele for één voor de lijst te doorlopen en de tweede voor data te adden
				XYSeriesCollection  dataset = new XYSeriesCollection ();
				for(int i=0;i<gegevensAlleAlgoritmen.size();i++){
					//één dataset van alle algoritmes is geselecteerd
					GlobalVarList data = gegevensAlleAlgoritmen.get(i);
					XYSeries hulp = new XYSeries( data.getAlgoritmeNaam() );
					for(int j = 0;j<data.getSize();j++){
						GlobalVar percentiel = data.getPercentiel(j);
						//System.out.println(percentiel.getAverageServiceTime()+" "+percentiel.getAverageNorRuntime());
						hulp.add(percentiel.getAverageServiceTime(),percentiel.getAverageWaittime());
					}
					dataset.addSeries(hulp);
				}
				return dataset;
	}
}


