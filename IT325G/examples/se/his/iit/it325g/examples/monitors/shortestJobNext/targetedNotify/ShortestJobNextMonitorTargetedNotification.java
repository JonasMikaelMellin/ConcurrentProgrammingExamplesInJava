package se.his.iit.it325g.examples.monitors.shortestJobNext.targetedNotify;

import java.util.HashMap;
import java.util.PriorityQueue;

import se.his.iit.it325g.common.AndrewsProcess;

/**¨
 * Target notification (https://www.safaribooksonline.com/library/view/java-threads-second/1565924185/ch04s03.html) shortest job next solution in Java. 
 * @author melj
 *
 */

public class ShortestJobNextMonitorTargetedNotification {
	private volatile Boolean free=true;
	private PriorityQueue<ResourceRequest> q=new PriorityQueue<ResourceRequest>();
	private TargetNotification waitingObject=new TargetNotification();
	
	private boolean checkIfFreeAndLockIfIAmFirst() {
		boolean cachedFree;
		synchronized(this.free) {
			synchronized(this.q) {
				if (this.free && this.q.peek().getId()==AndrewsProcess.currentAndrewsProcessId()) {
					this.free=false;
					cachedFree=true;
				} else {
					cachedFree=false;
				}
			}
		}
		return cachedFree;
	}
	
	/**
	 * Request object with deadline t. This use a fine grained solution on synchronization.
	 * @param time
	 */
	
	public void request(int time) {
		synchronized(this.q) {
			this.q.add(new ResourceRequest(AndrewsProcess.currentAndrewsProcessId(),time));
		}
		
		boolean cachedFree=this.checkIfFreeAndLockIfIAmFirst();


		while (!cachedFree) {
			waitingObject.waitOnId(AndrewsProcess.currentAndrewsProcessId());
			cachedFree=this.checkIfFreeAndLockIfIAmFirst();
		}
		synchronized(this.q) {
			this.q.poll(); // throw away element, since it is the first in the queue
		}
	}
	public  void release() {
		synchronized(this.free) {
			this.free=true; // mark as free
		}
		synchronized(this.q) {
			if (!this.q.isEmpty()) {
				this.waitingObject.notifyId(this.q.peek().getId());
			} 
		}
	}

}
