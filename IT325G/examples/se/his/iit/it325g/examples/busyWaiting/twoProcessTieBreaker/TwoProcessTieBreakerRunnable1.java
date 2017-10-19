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

package se.his.iit.it325g.examples.busyWaiting.twoProcessTieBreaker;

import se.his.iit.it325g.common.AndrewsProcess;


/**
 * @author melj
 * 
 * Java implementation of Tie breaker process solution from "Concurrent Programming" by Gregory andrews.
 * 
 * 
 *
 */
public class TwoProcessTieBreakerRunnable1 implements Runnable {

	
	@Override
	public void run() {
		while (true) {
			GlobalProgramState.in1=true;
			GlobalProgramState.last=1;
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is waiting to access critical section");
			while (GlobalProgramState.in2 && GlobalProgramState.last==1) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is in critical section");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			GlobalProgramState.in1=false;
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is not in critical section");
		}
	}
	

}
