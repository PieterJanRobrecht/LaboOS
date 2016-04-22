package data;

public class PageTableEntry {
	private int pid;
	private boolean presentBit;
	private boolean modifyBit;
	private int lastAccessTime;
	private int frameNumber;
	private int pageNumber;
	private int persistentToRam;
	private int ramToPersistent;
	
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

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "PageTableEntry [pid=" + pid + ", frameNumber=" + frameNumber + ", pageNumber=" + pageNumber + "]";
	}

	public int getPersistentToRam() {
		return persistentToRam;
	}

	public int getRamToPersistent() {
		return ramToPersistent;
	}

	public void setPersistentToRam(int persistentToRam) {
		this.persistentToRam = persistentToRam;
	}

	public void setRamToPersistent(int ramToPersistent) {
		this.ramToPersistent = ramToPersistent;
	}
	
	
}
