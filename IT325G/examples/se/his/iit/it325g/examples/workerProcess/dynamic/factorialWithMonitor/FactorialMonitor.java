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

package se.his.iit.it325g.examples.workerProcess.dynamic.factorialWithMonitor;
import se.his.iit.it325g.common.AndrewsProcess;


public class FactorialMonitor {

	public FactorialMonitor() {
	}
	
	public long factorial(long n) {
		return this.factorial(1,n);
	}
	private AndrewsProcess[] createAndStartWorkerProcesses(long start,long m,long end) {
		AndrewsProcess workerProcess[]=new AndrewsProcess[2];
		workerProcess[0]=new AndrewsProcess(new FactorialRunnable(this,start,m));
		workerProcess[1]=new AndrewsProcess(new FactorialRunnable(this,m+1,end));
		workerProcess[0].start();
		workerProcess[1].start();
		return workerProcess;
		
	}
	synchronized long factorial(long start,long end) {
		final long diff=end-start;
		final long m=(end-start)/2+start;
		if (diff==0L) {
			this.notifyAll();
			return start;
		} else if (diff==1L) {
			this.notifyAll();
			return start*end;
		} else {
			AndrewsProcess workerProcess[]=this.createAndStartWorkerProcesses(start,m,end);

			while (!((FactorialRunnable)workerProcess[0].getRunnable()).isCompleted()||!((FactorialRunnable)workerProcess[1].getRunnable()).isCompleted()) {
				try {
					this.wait();
				} catch (InterruptedException ie) {
					AndrewsProcess.defaultInterruptedExceptionHandling(ie);
				}
			}
			this.notifyAll();
			return ((FactorialRunnable)workerProcess[0].getRunnable()).getResult()*((FactorialRunnable)workerProcess[1].getRunnable()).getResult();
		}
	}
	
	public static void main(String argv[]) {
		FactorialMonitor factorialMonitor=new FactorialMonitor();
		System.out.println(factorialMonitor.factorial(25L));
	}

}
