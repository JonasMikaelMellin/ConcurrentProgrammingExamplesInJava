//    IT325G - Concurrent programming examples in Java
//    Copyright (C) 2017  Jonas Mikael Mellin
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

// based on figure 7.13


package se.his.iit.it325g.examples.messagePassing.diningPhilosophers;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

public class Philosopher implements Runnable {
	private Random r=new Random();

	private int first;
	private int second;
	@Override
	public void run() {
		first=AndrewsProcess.currentAndrewsProcessId();
		second=(first+1)%GlobalProgramState.n;
		if (AndrewsProcess.currentAndrewsProcessId()==GlobalProgramState.n-1) {
			final int tmp=first;first=second;second=tmp; // swap first && second for process n-1
		}
		while (true) {
			int wait1=(int)GlobalProgramState.fork.get(first).receive(); // receive token
			int wait2=(int)GlobalProgramState.fork.get(second).receive(); //receive token
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" is eating, tokens are ("+wait1+","+wait2+")");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt())%1000); // eat
			GlobalProgramState.fork.get(second).send(wait2+1); // pass token to next
			GlobalProgramState.fork.get(first).send(wait1+1); // pass token to next
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" is thinking");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt())%1000); //think
		}

		
	}

}
