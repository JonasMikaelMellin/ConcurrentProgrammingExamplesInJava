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

package se.his.iit.it325g.examples.workerProcess.dynamic.factorialWithJoin;

import se.his.iit.it325g.common.AndrewsProcess;

public class Factorial implements Runnable {
	private long start;
	private long end;
	private long result;
	
	public Factorial(long start,long end) {
		this.start=start;
		this.end=end;
	}
	
	public static long factorial(long n) {
		Factorial factorial=new Factorial(1L,n);
		AndrewsProcess ap=new AndrewsProcess(factorial);
		ap.start();
		try {
			ap.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return factorial.getResult();
	}
	
	private AndrewsProcess[] createAndStartProcesses(long start,long m, long end) {
		AndrewsProcess workerProcess[]=new AndrewsProcess[2];
		workerProcess[0]=new AndrewsProcess(new Factorial(start,m));
		workerProcess[1]=new AndrewsProcess(new Factorial(m+1,end));
		for (int i=0; i<2; ++i) {
			workerProcess[i].start();
		}
		for (int i=0; i<2; ++i) {
			try {
				workerProcess[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		return workerProcess;
	}
	
	public long factorial(long start,long end) {
		long diff=end-start;
		if (diff==0) {
			return start;
		} else if (diff==1) {
			return start*end;
		} else {
			long m=diff/2+start;
			AndrewsProcess workerProcess[]=createAndStartProcesses(start,m,end);

			return ((Factorial)workerProcess[0].getRunnable()).getResult()*((Factorial)workerProcess[1].getRunnable()).getResult();
			
		}
	}

	@Override
	public void run() {
		this.result=factorial(this.start,this.end);
		
	}
	public long getResult() {
		return this.result;
	}
	public static void main(String argv[]) {
		System.out.println(Factorial.factorial(5));
	}

}
