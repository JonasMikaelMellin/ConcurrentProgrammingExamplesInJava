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

package se.his.iit.it325g.nProcessBakeryAlgorithmWithBoundedDomain;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

import se.his.iit.it325g.common.AndrewsProcess;


/**
 * @author melj
 * 
 * Java implementation of Tie breaker process solution from "Concurrent Programming" by Gregory andrews.
 * 
 * 
 *
 */
public class NProcessBakeryAlgorithmWithBoundedDomainRunnable implements Runnable {
	static int n=10;
	static int turn[]=IntStream.generate(() -> -1).limit(n).toArray(); // defaults to n * -1
	
	@Override
	public void run() {
		final int i=AndrewsProcess.currentAndrewsProcessId();
		while (true) {
			turn[i]=0;
			synchronized(turn) {
				System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" "+Arrays.toString(turn));
			}
			turn[i]=(Arrays.stream(turn).reduce(-1, (a,b) -> ((a>b && (a-b)<=n && a>0 && b>0)||(a<b && (a-b)>n && a>0 && b>0)||(a>0 && b<=0)?a:b) )+1)%(2*n+1);
			synchronized(turn) {
				System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" "+Arrays.toString(turn));

			}
			System.out.println("Thread "+i+" is at stage "+turn[i]);
			for (int j=0; j<turn.length; ++j) { 
				if (j==i) continue;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
				}
				while (turn[j]!=-1 && ((turn[i]>turn[j] && turn[i]-turn[j]<=n)|| (turn[i]<turn[j] && turn[j]-turn[i]>n)||(turn[i]==turn[j])&& i>j)) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is in critical section");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			turn[i]=-1;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is not in critical section");
		}
	}
	
	public static void main(String argv[]) {
		AndrewsProcess[] process;
		try {
			process = AndrewsProcess.andrewsProcessFactory(n, NProcessBakeryAlgorithmWithBoundedDomainRunnable.class);
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
