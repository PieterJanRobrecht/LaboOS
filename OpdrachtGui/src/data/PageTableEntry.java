package data;

public class PageTableEntry {
	private boolean presentBit;
	private boolean modifyBit;
	private int lastAccessTime;
	private int frameNumber;
	
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
		return frameNumber;
	}

	public void setFrameNummer(int frameNummer) {
		this.frameNumber = frameNummer;
	}

	public PageTableEntry() {
		super();
	}
	
	
}
