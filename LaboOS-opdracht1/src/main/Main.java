package main;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import data.GlobalVarList;
import data.Processlist;
import gui.Gui;

public class Main {

	public static void main(String[] args) {
		try {
			File file = new File("processen10000.xml");
//			File file = new File("C:\\Users\\Pieter-Jan\\Documents\\workspace\\OS2_Opdracht1\\test.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Processlist.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Processlist procList = (Processlist) jaxbUnmarshaller.unmarshal(file);
			//System.out.println(procList);
			procList.sortArrivalTime();
			//Lijst met alle gegevens van de verschillende algoritmen
			List<GlobalVarList> gegevensAlleAlgoritmen = verwerkGegevens(procList);
			//maakGrafiek(gegevensAlleAlgoritmen);
			Gui gui = new Gui(gegevensAlleAlgoritmen);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private static List<GlobalVarList> verwerkGegevens(Processlist procList) {
		//Lijst waarin alle algoritmes kunnen komen
		GlobalVarList hulp;
		List<GlobalVarList> gegevensAlleAlgo = new ArrayList<GlobalVarList>();
		for(int i=0;i<2;i++){
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
			}
			procList.sortServiceTime();
			hulp.verwerkGegevens(procList);
			System.out.println("Gegevens zijn verwerkt");
			gegevensAlleAlgo.add(hulp);
			System.out.println("Gegevens zijn toegevoegd aan de lijst");
//			if(i == 1)
//			System.out.println(gegevensAlleAlgo.get(1).getElement().getAverageServiceTime());
		}
		return gegevensAlleAlgo;
	}
}
