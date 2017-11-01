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

import java.math.BigInteger;

import se.his.iit.it325g.common.AndrewsProcess;

public class Factorial implements Runnable {
	private BigInteger start;
	private BigInteger end;
	private BigInteger result;
	
	public Factorial(final BigInteger start,final BigInteger  end) {
		this.start=start;
		this.end=end;
	}
	
	
	public static BigInteger factorial(BigInteger n) {
		Factorial factorial=new Factorial(BigInteger.ONE, n);
		AndrewsProcess ap=new AndrewsProcess(factorial);
		ap.start();
		try {
			ap.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return factorial.getResult();
	}
	
	private AndrewsProcess[] createAndStartProcesses(final BigInteger start, final BigInteger m, final BigInteger end) {
		AndrewsProcess workerProcess[]=new AndrewsProcess[2];
		workerProcess[0]=new AndrewsProcess(new Factorial(start,m));
		workerProcess[1]=new AndrewsProcess(new Factorial(m.add(BigInteger.ONE),end));
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
	
	public BigInteger factorial(BigInteger start,BigInteger end) {
		BigInteger diff=end.subtract(start);
		if (diff.compareTo(BigInteger.ZERO)==0) {
			return start;
		} else if (diff.compareTo(BigInteger.ONE)==0) {
			return start.multiply(end);
		} else {
			BigInteger m=diff.divide(BigInteger.valueOf(2L)).add(start);
			AndrewsProcess workerProcess[]=createAndStartProcesses(start,m,end);

			return ((Factorial)workerProcess[0].getRunnable()).getResult().multiply(((Factorial)workerProcess[1].getRunnable()).getResult());
			
		}
	}

	@Override
	public void run() {
		this.result=factorial(this.start,this.end);
		
	}
	public BigInteger getResult() {
		return this.result;
	}
	public static void main(String argv[]) {
		System.out.println(Factorial.factorial(BigInteger.valueOf(1000L)));
	}

}
