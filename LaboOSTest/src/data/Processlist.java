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
		setList();
		double tijd=0;
		int processNummer=0;
		Process vorige=null;
		//Deze While lus kijkt welke processen er toe komen op het huidige tijdstip
		while(processNummer!=processenLijst.size()||!queue.isEmpty()){
				boolean gaan=true;
				while(gaan){
					if(processNummer!=processenLijst.size()){	
						if(tijd==processenLijst.get(processNummer).getArrivaltime()){
							Process bij=processenLijst.get(processNummer);
							//System.out.println(bij.getPid()+ " toegevoegd op " + tijd);
							queue.add(bij);
							processNummer++;
						}
						else gaan=false;
					}	
					else gaan=false;
				}	
			//Hier wordt het vorige process terug aan de queue toegevoegd
			if(vorige!=null && !vorige.getDone())queue.add(vorige);
			//Als de queue leeg is gaan we gewoon verder
			if(queue.isEmpty()){
				tijd++;
			}
			//Hier kiezen we ons process en zijn er 2 mogelijkheden
			else{
				Process p=queue.remove(0);

				for(int i=1;i<q;i++){
					gaan=true;
					//Zoeken naar toekomende processen
					while(gaan){
						if(processNummer!=processenLijst.size()){
							if((tijd+i)==processenLijst.get(processNummer).getArrivaltime()){
								Process bij=processenLijst.get(processNummer);
								//System.out.println(bij.getPid()+ " toegevoegd");
								queue.add(bij);
								bij.setRunningtime(0);
								bij.setWaittime(bij.getWaittime()+(q-i));
								processNummer++;
							}
							else gaan=false;
						}
						else gaan=false;
					}
				}

				//Ofwel zal het Process p niet klaar zijn na dit tijdsslot
				if(p.getServicetime()-p.getRunningtime()>q){
					//System.out.println(p.getPid()+" Krijgt CPU maar te groot op " + tijd);
					p.addRunningtime(q);
					//Wachttijden zetten voor de processen in de queue
					for(Process k:queue){
						k.setWaittime(k.getWaittime()+q);
					}

					vorige=p;
					tijd=tijd+q;
				}
				//Ofwel is dit het laatste tijdslot van Process p
				else{
					double z=p.getServicetime()-p.getRunningtime();
					p.addRunningtime(z);
					//System.out.println(p.getPid()+" Krijgt CPU voor de laatste keer " + tijd);
					//Wachttijden zetten voor de processen in de queue
					for(Process k:queue) {
						k.setWaittime(k.getWaittime() + z);
					}
					p.setEndtime(tijd+z);
					p.setDone();
					vorige=p;
					tijd=tijd+z;
				}
			}
			//Dit is om te voorkomen dat het algoritme te vroeg zou stoppen
			if(vorige!=null){	
				if(!vorige.getDone()&&queue.isEmpty()&&processNummer==processenLijst.size()){
					queue.add(vorige);
					vorige=null;
				}
			}	
			//System.out.println(tijd);
		}
		
	}
	
	public void voerMLFBuit(boolean macht){
		List<List<Process>>queues=new ArrayList<List<Process>>();
		for(int i=0;i<4;i++){
			List<Process>queue=new LinkedList<Process>();
			queues.add(queue);
		}
		sortArrivalTime();
		setList();
		double tijd=0;
		int duur=2;
		int processNummer=0;
		Process vorige=null;
		int vorigeQueue=0;
		//Deze While lus kijkt welke processen er toe komen op het huidige tijdstip
		while(processNummer!=processenLijst.size()||!allQueuesEmpty(queues)){
			Process p=null;
			boolean gaan=true;
			while(gaan){
				if(processNummer!=processenLijst.size()){	
					if(tijd==processenLijst.get(processNummer).getArrivaltime()){
						Process bij=processenLijst.get(processNummer);
						//System.out.println(bij.getPid()+ " toegevoegd op " + tijd);
						queues.get(0).add(bij);
						bij.setRunningtime(0);
						processNummer++;
					}
					else gaan=false;
				}	
				else gaan=false;
			}
			//Toevoegen van vorig Process aan de juist queue
			if(vorige!=null && !vorige.getDone()){
				if(vorigeQueue+1==queues.size()){
					queues.get(vorigeQueue).add(vorige);
				}
				else{
					queues.get(vorigeQueue+1).add(vorige);
				}
			}
			//Als de queues leeg zijn gaan we gewoon verder
			if(allQueuesEmpty(queues)){
				tijd++;
			}
			else{
				boolean nietGevonden=false;
				int i=0;
				//Dit zoekt eerst een Process in queue 0, dan in 1 etc
				while(!nietGevonden&&i<queues.size()){
					if(!queues.get(i).isEmpty()){
						List<Process> q=queues.get(i);
						p=q.remove(0);
						//System.out.println(p.getPid() +" Van de lijst gehaald");
						vorigeQueue=i;
						vorige=p;
						if(macht){
							duur=(int) Math.pow(2,(i));
						}
						else{
							duur=(int) (i+1);
						}
						nietGevonden=true;					
						}
					i++;
				}

				for(int j=1;j<duur;j++){
					gaan=true;
					//Zoeken naar toekomende processen
					while(gaan){
						if(processNummer!=processenLijst.size()){
							if((tijd+j)==processenLijst.get(processNummer).getArrivaltime()){
								Process bij=processenLijst.get(processNummer);
								//System.out.println(bij.getPid()+ " toegevoegd");
								queues.get(0).add(bij);
								bij.setRunningtime(0);
								bij.setWaittime(bij.getWaittime()+(duur-j));
								processNummer++;
							}
							else gaan=false;
						}
						else gaan=false;
					}
				}
				//Weer de 2 gevallen scheiden
				if(p.getServicetime()-p.getRunningtime()>duur){
					//System.out.println(p.getPid()+" Krijgt CPU maar te groot op " + tijd);
					p.addRunningtime(duur);
					//Wachttijden zetten voor de processen in de queue
					for(List<Process> eenqueue:queues){
						for(Process k:eenqueue){
							k.setWaittime(k.getWaittime()+duur);
						}
					}

				
					vorige=p;
					tijd=tijd+duur;
				}
				else{
					double z=p.getServicetime()-p.getRunningtime();
					p.addRunningtime(z);
					//System.out.println(p.getPid()+" Krijgt CPU voor de laatste keer " + tijd);
					//Wachttijden zetten voor de processen in de queue
					for(List<Process> eenqueue:queues){
						for(Process k:eenqueue){
							k.setWaittime(k.getWaittime()+z);
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
				if(!vorige.getDone()&&allQueuesEmpty(queues)&&processNummer==processenLijst.size()){
					if(vorigeQueue+1==queues.size()){
						queues.get(vorigeQueue).add(vorige);
					}
					else{
						queues.get(vorigeQueue+1).add(vorige);
					}
					vorige=null;
				}
			}	
		}	
			
	}

	
	private boolean allQueuesEmpty(List<List<Process>> queues) {
		boolean nietLeeg=false;
		int teller=0;
		while(!nietLeeg&&teller<queues.size()){
			if(!queues.get(teller).isEmpty())nietLeeg=true;
			else teller++;
		}
		return !nietLeeg;
	}
	
	private void setList(){
		for(Process p:processenLijst){
			p.setEndtime(0);
			p.setRunningtime(0);
			p.setWaittime(0);
			p.resetDone();
		}
		
	}
}	