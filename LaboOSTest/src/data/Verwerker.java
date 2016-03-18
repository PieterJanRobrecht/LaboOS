package data;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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

import gui.Gui;

public class Verwerker {
	private List<GlobalVarList> gegevensAlleAlgo;
	private File file;
	
	public Verwerker(){
		
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Processlist.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Processlist procList = (Processlist) jaxbUnmarshaller.unmarshal(file);
			procList.sortArrivalTime();
			//Lijst met alle gegevens van de verschillende algoritmen
			gegevensAlleAlgo = verwerkGegevens(procList);
			//Gui gui = new Gui(gegevensAlleAlgoritmen);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public List<GlobalVarList> getGegevensAlleAlgo() {
		return gegevensAlleAlgo;
	}

	public void setGegevensAlleAlgo(List<GlobalVarList> gegevensAlleAlgo) {
		this.gegevensAlleAlgo = gegevensAlleAlgo;
	}
	
	private static List<GlobalVarList> verwerkGegevens(Processlist procList) {
		//Lijst waarin alle algoritmes kunnen komen
		GlobalVarList hulp;
		List<GlobalVarList> gegevensAlleAlgo = new ArrayList<GlobalVarList>();
		for(int i=0;i<3;i++){
			hulp = new GlobalVarList();
			//hulp.clear();
			procList.sortArrivalTime();
			switch(i){
			case 0: 
				procList.voerFCFSUit();
				System.out.println("FCFS is uitgevoerd");
				hulp.setAlgoritmeNaam("FCFS");
				break;
			case 1 : 
				procList.voerHRRNUit();
				System.out.println("HRRN is uitgevoerd");
				hulp.setAlgoritmeNaam("HRRN");
				break;
			case 2 :
				procList.voerRRuit(2);
				System.out.println("RR is uitgevoerd");
				hulp.setAlgoritmeNaam("RR");
				break;
			}
			procList.sortServiceTime();
			hulp.verwerkGegevens(procList);
			System.out.println("Gegevens zijn verwerkt");
			gegevensAlleAlgo.add(hulp);
			System.out.println("Gegevens zijn toegevoegd aan de lijst");
		}
		return gegevensAlleAlgo;
	}

	public ChartPanel maakGrafiekPanel() {
		JFreeChart lineChart = maakGrafiek();

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		return chartPanel;
	}

	public ChartPanel maakGrafiekWaitPanel() {
		JFreeChart lineChart = maakGrafiekWait();

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		return chartPanel;
	}

	private XYDataset createDataset(List<GlobalVarList> gegevensAlleAlgoritmen) {
		//Lijst met alle gegevens van alle algoritmen worden aangeboden
		//Moeten dus verschillende lijnen maken voor alle objecten in de lijst apart
		//Miss best doen in een dubbele for ��n voor de lijst te doorlopen en de tweede voor data te adden
		XYSeriesCollection  dataset = new XYSeriesCollection ();
		for(int i=0;i<gegevensAlleAlgoritmen.size();i++){
			//��n dataset van alle algoritmes is geselecteerd
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
				//Miss best doen in een dubbele for ��n voor de lijst te doorlopen en de tweede voor data te adden
				XYSeriesCollection  dataset = new XYSeriesCollection ();
				for(int i=0;i<gegevensAlleAlgoritmen.size();i++){
					//��n dataset van alle algoritmes is geselecteerd
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

	public JFreeChart maakGrafiek(){
		JFreeChart lineChart = ChartFactory.createXYLineChart("Scheduling Algortimes", "Gemiddelde ServiceTime", "Normalised TAT", createDataset(gegevensAlleAlgo),
				PlotOrientation.VERTICAL, true, true, false);

		final XYPlot plot = lineChart.getXYPlot( );
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
		//Kleuren aanpassen voor alle grafieken
		//Momenteel is er maar 1 aanwezig dus de rest maakt niet echt uit
		renderer.setSeriesPaint( 0 , Color.RED );
		renderer.setSeriesPaint( 1 , Color.GREEN );
		renderer.setSeriesPaint( 2 , Color.PINK );
		final NumberAxis domainAxis = new LogarithmicAxis("Gemiddelde ServiceTime");
//	    final NumberAxis domainAxis = new NumberAxis("Gemiddelde ServiceTime");
		final NumberAxis rangeAxis = new LogarithmicAxis("Genormaliseerde TAT");
//      final NumberAxis rangeAxis = new NumberAxis("Genormaliseerde TAT");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(rangeAxis);
		plot.setRenderer( renderer );
		return lineChart;
	}

	public JFreeChart maakGrafiekWait(){
		JFreeChart lineChart = ChartFactory.createXYLineChart("Scheduling Algortimes", "Gemiddelde ServiceTime", "Gemiddelde WaitTime", createDatasetGrafiekWait(gegevensAlleAlgo),
				PlotOrientation.VERTICAL, true, true, false);
		final XYPlot plot = lineChart.getXYPlot( );
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
		//Kleuren aanpassen voor alle grafieken
		//Momenteel is er maar 1 aanwezig dus de rest maakt niet echt uit
		renderer.setSeriesPaint( 0 , Color.RED );
		renderer.setSeriesPaint( 1 , Color.GREEN );
		renderer.setSeriesPaint( 2 , Color.PINK );
		final NumberAxis domainAxis = new LogarithmicAxis("Gemiddelde ServiceTime");
//	    final NumberAxis domainAxis = new NumberAxis("Gemiddelde ServiceTime");
		final NumberAxis rangeAxis = new LogarithmicAxis("Gemiddelde WaitTime");
//      final NumberAxis rangeAxis = new NumberAxis("Gemiddelde WaitTime");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(rangeAxis);
		plot.setRenderer( renderer );
		return lineChart;
	}
}
