package data;
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
		// boolean bezet = false;
		for (int i = 0; i < processenLijst.size(); i++) {
			Process hulp = processenLijst.get(i);
			if (tijd <= hulp.getArrivaltime() && wachtLijst.size() == 0) {
				tijd = hulp.getArrivaltime();
				hulp.setWaittime(tijd - hulp.getArrivaltime());
				hulp.setEndtime(tijd + hulp.getServicetime());
				tijd += hulp.getServicetime();
			}
			else  {
				int j = i;
				while(j<processenLijst.size() &&tijd > processenLijst.get(j).getArrivaltime()){
					wachtLijst.add(processenLijst.get(j));
					j++;
				}
				i = j-1;
				
				//Alle wachttijden berekenen voor de wachtende processen
				for(Process p : wachtLijst){
					p.setWaittime(tijd-p.getArrivaltime());
				}
				//Sorteer volgens norRuntime
				Collections.sort(wachtLijst,(Process p1, Process p2) -> Double.compare(p2.getNorRuntime(),p1.getNorRuntime()));
				//Neem de eerste uit de wachtlijst en haal die er ook uit
				while(wachtLijst.size()!=0){
				Process uitvoeren = wachtLijst.get(0);
				wachtLijst.remove(0);
				uitvoeren.setEndtime(tijd + uitvoeren.getServicetime());
				tijd += uitvoeren.getServicetime();
				for(Process p : wachtLijst){
					p.setWaittime(tijd-p.getArrivaltime());
				}
				//Sorteer volgens norRuntime
				Collections.sort(wachtLijst,(Process p1, Process p2) -> Double.compare(p2.getNorRuntime(),p1.getNorRuntime()));
				}
			}
		}
	}
	
	public void voerRRuit(int q){
		List<Process>queue =new LinkedList<Process>();
		sortArrivalTime();
		double tijd=0;
		int processNummer=0;
		Process vorige=null;
		while(processNummer!=processenLijst.size()||!queue.isEmpty()){
				boolean gaan=true;
				while(gaan){
					if(processNummer!=processenLijst.size()){	
						if(tijd==processenLijst.get(processNummer).getArrivaltime()){
							Process bij=processenLijst.get(processNummer);
							//System.out.println(bij.getPid()+ " toegevoegd op " + tijd);
							queue.add(bij);
							bij.setRunningtime(0);
							processNummer++;
						}
						else gaan=false;
					}	
					else gaan=false;
				}	
			
			if(vorige!=null && !vorige.getDone())queue.add(vorige);
			if(queue.isEmpty()){
				tijd++;
			}
			else{
				Process p=queue.remove(0);
				if(p.getServicetime()-p.getRunningtime()>q){
					//System.out.println(p.getPid()+" Krijgt CPU maar te groot op " + tijd);
					p.addRunningtime(q);
					for(Process k:queue){
						k.setWaittime(k.getWaittime()+q);
					}
					for(int i=1;i<q;i++){		
							gaan=true;
							while(gaan){
								if(processNummer!=processenLijst.size()){	
									if((tijd+i)==processenLijst.get(processNummer).getArrivaltime()){
										Process bij=processenLijst.get(processNummer);
										//System.out.println(bij.getPid()+ " toegevoegd");
										queue.add(bij);
										bij.setRunningtime(0);
										processNummer++;
									}
									else gaan=false;
								}
								else gaan=false;
						}	
					}	
					if(p.getRunningtime()+(q-1)==p.getServicetime()){
						p.setEndtime(tijd+q);
						p.setDone();
						//System.out.println(p.getPid()+" done op tijdstip "+p.getEndtime());
						vorige=p;
					}
					else{
						vorige=p;
					}
					tijd=tijd+q;
				}
				else{
					double z=p.getServicetime()-p.getRunningtime();
					p.addRunningtime(z);
					//System.out.println(p.getPid()+" Krijgt CPU voor de laatste keer " + tijd);
					for(Process k:queue){
						k.setWaittime(k.getWaittime()+z);
					}
					for(int i=1;i<z;i++){		
							gaan=true;
							while(gaan){
								if(processNummer!=processenLijst.size()){
									if((tijd+i)==processenLijst.get(processNummer).getArrivaltime()){
										Process bij=processenLijst.get(processNummer);
										//System.out.println(bij.getPid()+ " toegevoegd");
										queue.add(bij);
										bij.setRunningtime(0);
										processNummer++;
									}
									else gaan=false;
								}
								else gaan=false;
						}	
					}	
					p.setEndtime(tijd+z);
					p.setDone();
					//System.out.println(p.getPid()+" done op tijdstip "+p.getEndtime());
					vorige=p;
					tijd=tijd+z;
				}
			}
			if(vorige!=null){	
				if(!vorige.getDone()&&queue.isEmpty()&&processNummer==processenLijst.size()){
					queue.add(vorige);
					vorige=null;
				}
			}	
			//System.out.println(tijd);
		}
		
	}
}	
