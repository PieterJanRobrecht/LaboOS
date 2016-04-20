package data;

public class PageTableEntry {
	private boolean presentBit;
	private boolean modifyBit;
	private int lastAccessTime;
	private int frameNumber;
	private int pageNumber;
	
	public PageTableEntry(){
		presentBit = false;
		modifyBit = false;
		lastAccessTime = 0;
		frameNumber = 0;
	}
	
	public PageTableEntry(boolean presentBit, boolean modifyBit, int lastAccessTime, int frameNumber) {
		super();
		this.presentBit = presentBit;
		this.modifyBit = modifyBit;
		this.lastAccessTime = lastAccessTime;
		this.frameNumber = frameNumber;
	}

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

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNummer) {
		this.frameNumber = frameNummer;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
}
