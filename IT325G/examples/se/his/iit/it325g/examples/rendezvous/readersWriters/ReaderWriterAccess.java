package se.his.iit.it325g.examples.rendezvous.readersWriters;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.rendezvous.Entry;

public class ReaderWriterAccess {
	private ReaderWriterAccessServer rwas;
	private Entry acquireRead_Entry;
	private Entry releaseRead_Entry;
	private Entry acquireWrite_Entry;
	private Entry releaseWrite_Entry;
	public ReaderWriterAccess(final AndrewsProcess andrewsProcess) {
		if (!(andrewsProcess.getRunnable() instanceof ReaderWriterAccessServer)) {
			throw new IllegalArgumentException("Expected an ReaderWriterAccessServer object, got an \""+andrewsProcess.getRunnable().getClass().getName()+"\" instead");
		}
		rwas=(ReaderWriterAccessServer)andrewsProcess.getRunnable();
		this.acquireRead_Entry = rwas.getEntry("acquireRead_Entry");
		this.releaseRead_Entry = rwas.getEntry("releaseRead_Entry");
		this.acquireWrite_Entry = rwas.getEntry("acquireWrite_Entry");
		this.releaseWrite_Entry = rwas.getEntry("releaseWrite_Entry");
		
	}
	
	public void acquireRead() {
		this.acquireRead_Entry.call();
		
	}
	public void releaseRead() {
		this.releaseRead_Entry.call();
		
	}
	public void acquireWrite() {
		this.acquireWrite_Entry.call();
		
	}
	public void releaseWrite() {
		this.releaseWrite_Entry.call();
		
	}

}
