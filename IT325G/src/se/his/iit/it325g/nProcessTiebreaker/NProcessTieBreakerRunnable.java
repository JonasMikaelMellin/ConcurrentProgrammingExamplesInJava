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

package se.his.iit.it325g.nProcessTiebreaker;

import se.his.iit.it325g.common.AndrewsProcess;


/**
 * @author melj
 * 
 * Java implementation of Tie breaker process solution from "Concurrent Programming" by Gregory andrews.
 * 
 * 
 *
 */
public class NProcessTieBreakerRunnable implements Runnable {
	static int n=10;
	static int in[]=new int[n],last[]=new int[n];
	
	@Override
	public void run() {
		while (true) {
			for (int j=0; j<in.length-1; ++j) {
				in[AndrewsProcess.currentAndrewsProcessId()]=j;
				last[j]=AndrewsProcess.currentAndrewsProcessId();
				System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is at stage "+j);
				for (int k=0; k<in.length; ++k) { 
					if (k==AndrewsProcess.currentAndrewsProcessId()) continue;
					while (in[k]>=in[AndrewsProcess.currentAndrewsProcessId()] && last[j]==AndrewsProcess.currentAndrewsProcessId()) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
						}
					}
				}
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is in critical section");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			in[AndrewsProcess.currentAndrewsProcessId()]=0;
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is not in critical section");
		}
	}
	
	public static void main(String argv[]) {
		AndrewsProcess[] process;
		try {
			process = AndrewsProcess.andrewsProcessFactory(n, NProcessTieBreakerRunnable.class);
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
