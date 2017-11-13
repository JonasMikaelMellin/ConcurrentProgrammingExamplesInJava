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

package se.his.iit.it325g.examples.monitors.diningPhilosophers;

import java.util.PriorityQueue;

import se.his.iit.it325g.common.AndrewsProcess;

/**¨
 * Conventional shortest job next solution in Java. 
 * @author melj
 *
 */

public class EatingController {
	private boolean eating[]=new boolean[GlobalProgramState.n];
	
	public synchronized void request(int phid) {
		int left=(phid+1)%GlobalProgramState.n;
		int right=(phid-1)%GlobalProgramState.n;
		if (right<0) {
			right=GlobalProgramState.n+right;
		}
		if (left==0) {
			int tmp=right;
			right=left;
			left=tmp;
		} 

		while (this.eating[left] || this.eating[right]) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				AndrewsProcess.defaultInterruptedExceptionHandling(e);
			}
		}
		this.eating[phid]=true;
	}
	public synchronized void release(int phid) {
		this.eating[phid]=false;
		this.notifyAll();
	}

}
