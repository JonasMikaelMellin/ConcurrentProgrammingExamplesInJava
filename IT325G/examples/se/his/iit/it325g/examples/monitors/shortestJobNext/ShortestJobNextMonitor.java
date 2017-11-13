package se.his.iit.it325g.examples.monitors.shortestJobNext;

import java.util.PriorityQueue;

import se.his.iit.it325g.common.AndrewsProcess;

/**¨
 * Conventional shortest job next solution in Java. 
 * @author melj
 *
 */

public class ShortestJobNextMonitor {
	private boolean free=true;
	private PriorityQueue<ResourceRequest> q=new PriorityQueue<ResourceRequest>();
	
	public synchronized void request(int time) {
		this.q.add(new ResourceRequest(AndrewsProcess.currentAndrewsProcessId(),time));
		
		// no ordering, arbitrary process woken up, hence it is necessary to check if
		// the woken process is actually the first one in the queue. Further, even if 
		// ordering would have been preserved, we still do not want FCFS (or FIFO)
		// ordering

		while (!this.free || this.q.peek().getId()!=AndrewsProcess.currentAndrewsProcessId()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				AndrewsProcess.defaultInterruptedExceptionHandling(e);
			}
		}
		this.q.poll(); // throw away element, since it is the first in the queue
		this.free=false; // mark as non-free
	}
	public synchronized void release() {
		this.free=true; // mark as free
		if (!this.q.isEmpty()) {
			this.notifyAll(); // if there are queuing processes -> wake them up
		} 
	}

}
