import org.jfree.chart.ChartPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineChart_AWT extends ApplicationFrame {
	public LineChart_AWT(String applicationTitle, String chartTitle, List<GlobalVarList> gegevensAlleAlgoritmen) {
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createXYLineChart(chartTitle, "Gemiddelde ServiceTime", "Normalised TAT", createDataset(gegevensAlleAlgoritmen),
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
	    renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
	    renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
	    renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
	    plot.setRenderer( renderer );
		
		setContentPane(chartPanel);
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