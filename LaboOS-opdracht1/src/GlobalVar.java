import java.util.*;

public class GlobalVar {
	private List<Process> processenPercentiel;
	private double averageRuntime, averageNorRuntime, averageWaittime,percentielNummer,averageServiceTime;
	
	public GlobalVar(List<Process> lijst,int nummer){
		processenPercentiel = lijst;
		percentielNummer = nummer;
		initvar();
	}

	public List<Process> getProcessenPercentiel() {
		return processenPercentiel;
	}

	public void setProcessenPercentiel(List<Process> processenPercentiel) {
		this.processenPercentiel = processenPercentiel;
	}

	public double getAverageRuntime() {
		return averageRuntime;
	}

	public void setAverageRuntime(double averageRuntime) {
		this.averageRuntime = averageRuntime;
	}

	public double getAverageNorRuntime() {
		return averageNorRuntime;
	}

	public void setAverageNorRuntime(double averageNorRuntime) {
		this.averageNorRuntime = averageNorRuntime;
	}

	public double getAverageWaittime() {
		return averageWaittime;
	}

	public void setAverageWaittime(double averageWaittime) {
		this.averageWaittime = averageWaittime;
	}

	public double getPercentielNummer() {
		return percentielNummer;
	}

	public void setPercentielNummer(double percentielNummer) {
		this.percentielNummer = percentielNummer;
	}

	private void initvar() {
		//Kunnen ervan uitgaan dat de lijst volgens servicetime zijn gesorteerd
		averageNorRuntime=0;
		averageRuntime=0;
		averageWaittime=0;
		averageServiceTime=0;
		for(int i=0;i<processenPercentiel.size();i++){
			Process hulp = processenPercentiel.get(i);
			averageNorRuntime+=hulp.getNorRuntime();
			averageRuntime+=hulp.getRuntime();
			averageWaittime+=hulp.getWaittime();
			averageServiceTime+=hulp.getServicetime();
		}
		averageNorRuntime=averageNorRuntime/processenPercentiel.size();
		averageRuntime=averageRuntime/processenPercentiel.size();
		averageWaittime=averageWaittime/processenPercentiel.size();
		averageServiceTime=averageServiceTime/processenPercentiel.size();
	}

	public double getAverageServiceTime() {
		return averageServiceTime;
	}

	public void setAverageServiceTime(double averageServiceTime) {
		this.averageServiceTime = averageServiceTime;
	}
}
