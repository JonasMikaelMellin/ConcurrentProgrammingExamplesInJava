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



public class FactorialRunnable implements Runnable {
	
	private FactorialMonitor factorialMonitor;
	private long start;
	private long end;
	private boolean isCompleted=false;
	private long result;
	

	/**
	 * @param factorialMonitor
	 */
	public FactorialRunnable(final FactorialMonitor factorialMonitor, long start,long end) {
		this.factorialMonitor = factorialMonitor;
		this.start=start;
		this.end=end;
	}


	@Override
	public void run() {
		// calls back to monitor
		this.result=this.factorialMonitor.factorial(this.start,this.end);
		this.isCompleted=true;
		
		
	}
	public final long getResult() {
		return this.result;
	}


	public boolean isCompleted() {
		return this.isCompleted;
	}

}
