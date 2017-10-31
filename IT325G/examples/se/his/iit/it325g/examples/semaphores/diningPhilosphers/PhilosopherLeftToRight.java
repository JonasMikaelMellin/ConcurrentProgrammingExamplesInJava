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


package se.his.iit.it325g.examples.semaphores.diningPhilosphers;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

public class PhilosopherLeftToRight implements Runnable {

	public PhilosopherLeftToRight() {
	}

	@Override
	public void run() {
		final int left=4;
		final int right=0;
		Random r=new Random(left);
		
		while (true) {

			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" is awating fork "+right+" and "+left);
			GlobalProgramState.fork[left].P();
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" is awating fork "+left);
			GlobalProgramState.fork[right].P();
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" is eating");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(1000)));
			GlobalProgramState.fork[right].V();
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" released fork "+right);
			GlobalProgramState.fork[left].V();
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" released fork "+left);
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" is thinking");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(1000)));
		}

	}

}
