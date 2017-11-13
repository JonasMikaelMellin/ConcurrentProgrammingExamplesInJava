//    IT325G - Concurrent programming examples in Java
//    Copyright (C) 2017  Jonas Mikael Mellin
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.


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
