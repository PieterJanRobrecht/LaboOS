import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "processlist")
public class Processlist {
	private List<Process> processenLijst;

	public Processlist() {

	}

	public List<Process> getProcessenLijst() {
		return processenLijst;
	}

	@XmlElement(name = "process")
	public void setProcessenLijst(List<Process> processenLijst) {
		this.processenLijst = processenLijst;
	}

	public int getSize() {
		return processenLijst.size();
	}

	public void sortArrivalTime() {
		Collections.sort(processenLijst, (Process p1, Process p2) ->Double.compare( p1.getArrivaltime(),p2.getArrivaltime()));
	}

	public void sortServiceTime() {
		Collections.sort(processenLijst, (Process p1, Process p2) -> Double.compare(p1.getServicetime(), p2.getServicetime()));
	}

	public List<Process> verdeelLijst(int i, int aantalElementenPerPercentiel) {
		List<Process> deelLijst = processenLijst.subList(i * aantalElementenPerPercentiel,
				(i + 1) * aantalElementenPerPercentiel - 1);
		return deelLijst;
	}

	// Uitvoering van het FCFS algo
	public void voerFCFSUit() {
		// We beginnen bij het eerste proces
		double tijd = 0;
		for (int i = 0; i < processenLijst.size(); i++) {
			Process hulp = processenLijst.get(i);
			if (tijd < hulp.getArrivaltime()) {
				tijd = hulp.getArrivaltime();
			}
			hulp.setWaittime(tijd - hulp.getArrivaltime());
			hulp.setEndtime(tijd + hulp.getServicetime());
			tijd += hulp.getServicetime();
		}
	}

	public void voerHRRNUit() {
		List<Process> wachtLijst = new ArrayList<Process>();
		double tijd = 0;
		//boolean bezet = false;
		for (int i = 0; i < processenLijst.size(); i++) {
			Process hulp = processenLijst.get(i);
			if (tijd < hulp.getArrivaltime() && wachtLijst.size() == 0) {
				tijd = hulp.getArrivaltime();
			}
			if (tijd > hulp.getArrivaltime()) {
				wachtLijst.add(hulp);
			}
			if(wachtLijst.size()!=0 && hulp.getArrivaltime()>tijd){
				//Alle wachttijden berekenen voor de wachtende processen
				for(Process p : wachtLijst){
					p.setWaittime(tijd-p.getArrivaltime());
				}
				//Sorteer volgens norRuntime
				Collections.sort(wachtLijst,(Process p1, Process p2) -> Double.compare(p1.getNorRuntime(),p2.getNorRuntime()));
				//Neem de eerste uit de wachtlijst en haal die er ook uit
				Process uitvoeren = wachtLijst.get(0);
				wachtLijst.remove(0);
				uitvoeren.setEndtime(tijd + uitvoeren.getServicetime());
				tijd += uitvoeren.getServicetime();
			}else{
				hulp.setWaittime(tijd - hulp.getArrivaltime());
				hulp.setEndtime(tijd + hulp.getServicetime());
				tijd += hulp.getServicetime();
			}
		}

	}

}
