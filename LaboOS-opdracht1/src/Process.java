import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType( propOrder = { "pid", "arrivaltime", "servicetime","endtime","runtime","norRuntime","waittime" })
@XmlRootElement( name = "process")
public class Process {
	private double pid,arrivaltime,servicetime;
	private double endtime,runtime,norRuntime,waittime;
	
	public double getEndtime() {
		return endtime;
	}

	public void setEndtime(double d) {
		this.endtime = d;
	}

	public double getRuntime() {
		runtime = servicetime + waittime;
		return runtime;
	}

//	public void setRuntime(double runtime) {
//		this.runtime = runtime;
//	}

	public double getNorRuntime() {
		norRuntime = (servicetime+waittime)/servicetime;
		return norRuntime;
	}

//	public void setNorRuntime(double norRuntime) {
//		this.norRuntime = norRuntime;
//	}

	@Override
	public String toString() {
		return "Process " + pid + ",";
	}

	public double getWaittime() {
		return waittime;
	}

	public void setWaittime(double d) {
		this.waittime = d;
	}

	public double getPid() {
		return pid;
	}
	
	@XmlElement(name = "pid")
	public void setPid(double pid) {
		this.pid = pid;
	}

	public double getArrivaltime() {
		return arrivaltime;
	}
	
	@XmlElement(name = "arrivaltime")
	public void setArrivaltime(double arrivaltime) {
		this.arrivaltime = arrivaltime;
	}

	public double getServicetime() {
		return servicetime;
	}
	
	@XmlElement(name = "servicetime")
	public void setServicetime(double servicetime) {
		this.servicetime = servicetime;
	}
	
}
