package listeners;

import data.Verwerker;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Pieter-Jan on 18/03/2016.
 */
public class listenerExport implements ActionListener {
	private Verwerker verwerker;

	public listenerExport(Verwerker verwerker) {
		this.verwerker = verwerker;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int hulp = 0;
		for(int i=0;i<3;i++) {
			switch (i) {
				case 0:
					verwerker.setFile(new File("processen10000.xml"));
					hulp = 10000;
					break;
				case 1:
					verwerker.setFile(new File("processen20000.xml"));
					hulp = 20000;
					break;
				case 2:
					verwerker.setFile(new File("processen50000.xml"));
					hulp = 50000;
					break;
			}
			File f = new File("Grafieken");
			if(!f.exists())
			f.mkdir();
			JFreeChart chartNorTat = verwerker.maakGrafiek();
			try {
				ChartUtilities.saveChartAsPNG(new File("Grafieken\\NorTAT"+hulp+".png"),chartNorTat,700,500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JFreeChart chartWait = verwerker.maakGrafiekWait();
			try {
				ChartUtilities.saveChartAsPNG(new File("Grafieken\\Wait"+hulp+".png"),chartWait,700,500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("-----------------------------------------------------------------------");
			System.out.println(hulp+" processen grafieken gemaakt");
			System.out.println("-----------------------------------------------------------------------");
		}
	}
}
