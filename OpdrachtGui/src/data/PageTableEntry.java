package data;

public class PageTableEntry {
	private boolean presentBit;
	private boolean modifyBit;
	private int lastAccessTime;
	private int frameNummer;
	
	public boolean isPresentBit() {
		return presentBit;
	}

	public void setPresentBit(boolean presentBit) {
		this.presentBit = presentBit;
	}

	public boolean isModifyBit() {
		return modifyBit;
	}

	public void setModifyBit(boolean modifyBit) {
		this.modifyBit = modifyBit;
	}

	public int getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(int lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getFrameNummer() {
		return frameNummer;
	}

	public void setFrameNummer(int frameNummer) {
		this.frameNummer = frameNummer;
	}

	public PageTableEntry() {
		super();
	}
	
	
}
