package data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "instructionlist")
public class InstructionList {

	@XmlElement(name = "instruction")
	private List<Instruction> instructies;

	public InstructionList() {
		super();
	}

	public List<Instruction> getInstructies() {
		return instructies;
	}

	public void setInstructies(List<Instruction> instructies) {
		this.instructies = instructies;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(Instruction i : instructies){
			sb.append(i.toString());
		}
		return sb.toString();
	}
	
	public int getSize(){
		return instructies.size();
	}
	
	public Instruction get(int klok){
		return instructies.get(klok);
	}
}
