package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

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

public class Gui extends JFrame {
	private JFrame mijnFrame;
	private JPanel panel;
	private ChartPanel chart;
	private JPanel knoppenPanel;
	private JButton xlm1;
	private JButton xml2;
	private JButton xml3;
	private JSplitPane split;

	public Gui(List<GlobalVarList> gegevensAlleAlgoritmen) {
		//panel waar alle gui elementen in moeten komen
		panel = new JPanel();
		mijnFrame = new JFrame();
		mijnFrame.setBounds(200, 100, 1000, 1000);
		
		//KnoppenPanel maken
		knoppenPanel = new JPanel();
		xlm1 = new JButton("Toevoegen xml1");
		//jury.addActionListener(new ListenerToevoegenJury(jl));
		xml2 = new JButton("Toevoegen xml2");
		//afdeling.addActionListener(new ListenerToevoegenAfdeling(al));
		xml3 = new JButton("Toevoegen xml3");
		//score.addActionListener(new ListenerToevoegenScore(al,jl));
		knoppenPanel.add(xlm1);
		knoppenPanel.add(xml2);
		knoppenPanel.add(xml3);
		//knoppenPanel.add(juryScroll);
		
		//Maken van grafiek
		chart = maakChartPanel(gegevensAlleAlgoritmen);
		JPanel chartPanel = new JPanel();
		chartPanel.add(chart);
		
		//Maken splitpanel
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,knoppenPanel,chartPanel);
		split.setDividerLocation(250);	
		split.setPreferredSize(new Dimension(700, 700)); 
		panel.add(split);
		
		mijnFrame.getContentPane().add(panel);
		mijnFrame.setVisible(true);
		mijnFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
	    final NumberAxis domainAxis = new LogarithmicAxis("Log(x)");
//	    final NumberAxis domainAxis = new NumberAxis("x");
        final NumberAxis rangeAxis = new LogarithmicAxis("Log(y)");
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
				System.out.println(percentiel.getAverageServiceTime()+" "+percentiel.getAverageNorRuntime());
				hulp.add(percentiel.getAverageServiceTime(),percentiel.getAverageNorRuntime());
			}
			dataset.addSeries(hulp);
		}
		return dataset;
	}
}


