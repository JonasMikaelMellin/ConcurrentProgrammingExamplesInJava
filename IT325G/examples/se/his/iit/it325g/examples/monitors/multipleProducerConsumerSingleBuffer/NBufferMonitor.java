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


package se.his.iit.it325g.examples.monitors.multipleProducerConsumerSingleBuffer;

import se.his.iit.it325g.common.AndrewsProcess;

public class NBufferMonitor {
	private int buffer[]=new int[GlobalProgramState.n];
	private int f=0, r=0, cnt=0;

	public synchronized void deposit(int value) {
		while (cnt==GlobalProgramState.n) {
			try {
				this.wait();
			} catch (InterruptedException ie) {
				AndrewsProcess.defaultInterruptedExceptionHandling(ie);
			}
		}
		this.buffer[this.r]=value;
		this.r=(this.r + 1)%GlobalProgramState.n;
		++this.cnt;
		this.notifyAll();
	}
	
	public synchronized int fetch() {
		while (this.cnt==0) {
			try {
				this.wait();
			} catch (InterruptedException ie) {
				AndrewsProcess.defaultInterruptedExceptionHandling(ie);

			}
			
		}
		final int value=this.buffer[this.f];
		this.f=(this.f + 1) % GlobalProgramState.n;
		--this.cnt;
		this.notifyAll();
		return value;
	}

}
