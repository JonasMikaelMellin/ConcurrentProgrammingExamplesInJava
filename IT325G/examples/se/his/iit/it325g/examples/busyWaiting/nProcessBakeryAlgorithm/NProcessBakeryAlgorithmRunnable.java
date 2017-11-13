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

package se.his.iit.it325g.examples.busyWaiting.nProcessBakeryAlgorithm;


import java.util.Arrays;

import se.his.iit.it325g.common.AndrewsProcess;


/**
 * @author melj
 * 
 * Java implementation of Tie breaker process solution from "Concurrent Programming" by Gregory andrews.
 * 
 * 
 *
 */
public class NProcessBakeryAlgorithmRunnable implements Runnable {
	
	@Override
	public void run() {
		final int i=AndrewsProcess.currentAndrewsProcessId();
		while (true) {
			GlobalProgramState.turn[i]=0;
			GlobalProgramState.turn[i]=Arrays.stream(GlobalProgramState.turn).reduce(-1, (a,b) -> Integer.max(a,b))+1;
			System.out.println("Thread "+i+" is at stage "+GlobalProgramState.turn[i]);
			for (int j=0; j<GlobalProgramState.turn.length; ++j) { 
				if (j==i) continue;
				AndrewsProcess.uninterruptibleMinimumDelay(10);

				while (GlobalProgramState.turn[j]!=-1 && (GlobalProgramState.turn[i]>GlobalProgramState.turn[j] ||(GlobalProgramState.turn[i]==GlobalProgramState.turn[j])&& i>j)) {
					AndrewsProcess.uninterruptibleMinimumDelay(10);
				}
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is in critical section");
			
			AndrewsProcess.uninterruptibleMinimumDelay(10);
			GlobalProgramState.turn[i]=-1;

			AndrewsProcess.uninterruptibleMinimumDelay(10);
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is not in critical section");
		}
	}
	

}
