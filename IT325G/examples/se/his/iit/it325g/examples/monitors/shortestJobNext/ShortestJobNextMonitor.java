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
