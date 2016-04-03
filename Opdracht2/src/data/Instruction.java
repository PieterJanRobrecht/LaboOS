package data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "instruction")
public class Instruction {

	@XmlElement(name = "processID")
	private int pid;

	@XmlElement(name = "operation")
	private String operation;

	@XmlElement(name = "address")
	private long address;

	public Instruction() {
		super();
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public long getAddress() {
		return address;
	}

	public void setAddress(long address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Instruction [pid=" + pid + ", operation=" + operation + ", address=" + address + "]\n";
	}
}
