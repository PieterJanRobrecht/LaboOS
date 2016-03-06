package data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
				procList.voerRRuit();
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
}
