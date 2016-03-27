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
		for(int i=0;i<7;i++){
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
					System.out.println("RR q = 2 is uitgevoerd");
					hulp.setAlgoritmeNaam("RR q = 2");
					break;
				case 3 :
					procList.voerRRuit(4);
					System.out.println("RR q = 4 is uitgevoerd");
					hulp.setAlgoritmeNaam("RR q = 4");
					break;
				case 4 :
					procList.voerRRuit(8);
					System.out.println("RR q = 8 is uitgevoerd");
					hulp.setAlgoritmeNaam("RR q = 8");
					break;
				case 5:
					procList.voerMLFBuit(true);
					System.out.println("MLFB q = 2^i is uitgevoerd");
					hulp.setAlgoritmeNaam("MLFB q = 2^i");
					break;
				case 6 :
					procList.voerMLFBuit(false);
					System.out.println("MLFB q = i is uitgevoerd");
					hulp.setAlgoritmeNaam("MLFB q = i");
					break;
			}
			procList.sortServiceTime();
			hulp.verwerkGegevens(procList);
			System.out.println("Gegevens zijn verwerkt");
			gegevensAlleAlgo.add(hulp);
			System.out.println("Gegevens zijn toegevoegd aan de lijst");
		}
//		System.out.println(gegevensAlleAlgo.size());
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
		for (int i = 0; i < gegevensAlleAlgoritmen.size(); i++) {
			GlobalVarList data = gegevensAlleAlgoritmen.get(i);
			//��n dataset van alle algoritmes is geselecteerd
			XYSeries hulp = new XYSeries(data.getAlgoritmeNaam());
			for (int j = 0; j < data.getSize(); j++) {
				GlobalVar percentiel = data.getPercentiel(j);
				//System.out.println(percentiel.getAverageServiceTime()+" "+percentiel.getAverageNorRuntime());
				hulp.add(percentiel.getAverageServiceTime(), percentiel.getAverageNorRuntime());
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
		for(int i=0;i<gegevensAlleAlgoritmen.size();i++) {
			//��n dataset van alle algoritmes is geselecteerd
			GlobalVarList data = gegevensAlleAlgoritmen.get(i);
			XYSeries hulp = new XYSeries(data.getAlgoritmeNaam());
			for (int j = 0; j < data.getSize(); j++) {
				GlobalVar percentiel = data.getPercentiel(j);
				//System.out.println(percentiel.getAverageServiceTime()+" "+percentiel.getAverageNorRuntime());
				hulp.add(percentiel.getAverageServiceTime(), percentiel.getAverageWaittime());
			}
			dataset.addSeries(hulp);
		}
		return dataset;
	}

	public JFreeChart maakGrafiek(){
		JFreeChart lineChart = ChartFactory.createXYLineChart("Scheduling Algoritmes", "Gemiddelde ServiceTime", "Normalised TAT", createDataset(gegevensAlleAlgo),
				PlotOrientation.VERTICAL, true, true, false);
		maakPlot(lineChart);
		XYPlot plot = lineChart.getXYPlot( );

		//Het maken van de logaritmische schaalverdeling
		final NumberAxis domainAxis = new LogarithmicAxis("Gemiddelde ServiceTime");
//	    final NumberAxis domainAxis = new NumberAxis("Gemiddelde ServiceTime");
		final NumberAxis rangeAxis = new LogarithmicAxis("Gemiddelde Genormaliseerde TAT");
//      final NumberAxis rangeAxis = new NumberAxis("Gemiddelde WaitTime");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(rangeAxis);

		return lineChart;
	}

	public JFreeChart maakGrafiekWait(){
		JFreeChart lineChart = ChartFactory.createXYLineChart("Scheduling Algoritmes", "Gemiddelde ServiceTime", "Gemiddelde WaitTime", createDatasetGrafiekWait(gegevensAlleAlgo),
				PlotOrientation.VERTICAL, true, true, false);
		maakPlot(lineChart);
		XYPlot plot = lineChart.getXYPlot( );

		//Het maken van de logaritmische schaalverdeling
		final NumberAxis domainAxis = new LogarithmicAxis("Gemiddelde ServiceTime");
//	    final NumberAxis domainAxis = new NumberAxis("Gemiddelde ServiceTime");
		final NumberAxis rangeAxis = new LogarithmicAxis("Gemiddelde WaitTime");
//      final NumberAxis rangeAxis = new NumberAxis("Gemiddelde WaitTime");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(rangeAxis);

		return lineChart;
	}


	//Alle aanpassingen aan het uiterlijk van de grafieken gebeuren hier
	private void maakPlot(JFreeChart lineChart){
		final XYPlot plot = lineChart.getXYPlot( );
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
		//Kleuren aanpassen voor alle grafieken
		renderer.setSeriesPaint( 0 , Color.RED );
		renderer.setSeriesPaint( 1 , Color.GREEN );
		renderer.setSeriesPaint( 2 , Color.PINK );
		for (int i=3;i<gegevensAlleAlgo.size();i++)
			renderer.setSeriesPaint(i,Color.BLUE);
		for(int i=0;i<gegevensAlleAlgo.size();i++){
			renderer.setSeriesShapesVisible(i,false);
		}

		//Maken van een witte achtergrond en dat het grid in het grijs is
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.GRAY);
		plot.setRangeGridlinePaint(Color.GRAY);

		plot.setRenderer( renderer );
	}
}
