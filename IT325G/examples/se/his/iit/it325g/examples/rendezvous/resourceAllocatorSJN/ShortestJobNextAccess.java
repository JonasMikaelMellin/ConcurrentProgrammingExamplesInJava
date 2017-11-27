package se.his.iit.it325g.examples.rendezvous.resourceAllocatorSJN;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.rendezvous.Entry;
import se.his.iit.it325g.common.rendezvous.Result;
import se.his.iit.it325g.examples.rendezvous.readersWriters.ReaderWriterAccessServer;

public class ShortestJobNextAccess {

	private ShortestJobNextServer sjns;
	private Entry request_Entry;
	private Entry release_Entry;
	public ShortestJobNextAccess(final AndrewsProcess andrewsProcess) {
		if (!(andrewsProcess.getRunnable() instanceof ShortestJobNextServer)) {
			throw new IllegalArgumentException("Expected an ShortestJobNextServer object, got an \""+andrewsProcess.getRunnable().getClass().getName()+"\" instead");
		}
		sjns=(ShortestJobNextServer)andrewsProcess.getRunnable();
		this.request_Entry = sjns.getEntry("request_Entry");
		this.release_Entry = sjns.getEntry("release_Entry");
		
	}
	
	public Integer request(Integer priority) {
		this.request_Entry.call(priority);
		return ((Result<Integer>)this.request_Entry.getAction().getResult()).getObject();
		
	}
	public void release(Integer unit) {
		this.release_Entry.call(unit);
		
	}


}
