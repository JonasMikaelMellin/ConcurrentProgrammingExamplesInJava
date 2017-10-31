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

package se.his.iit.it325g.examples.semaphores.diningPhilosphers;


import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;
import se.his.iit.it325g.common.AndrewsSemaphore;

public class GlobalProgramState {
	
	static AndrewsSemaphore fork[]=new AndrewsSemaphore[5];


	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());
		
		for (int i=0; i<GlobalProgramState.fork.length; ++i) {
			fork[i]=new AndrewsSemaphore(1);
		}

		RunnableSpecification rs[]=new RunnableSpecification[2];
		AndrewsProcess[] process;
		try {
			rs[0]=new RunnableSpecification(PhilosopherRightToLeft.class,4);
			rs[1]=new RunnableSpecification(PhilosopherLeftToRight.class,1);
			process = AndrewsProcess.andrewsProcessFactory(rs);
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
