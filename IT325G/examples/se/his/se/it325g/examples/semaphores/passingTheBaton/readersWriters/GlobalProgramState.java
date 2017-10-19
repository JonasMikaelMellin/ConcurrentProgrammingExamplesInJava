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

package se.his.se.it325g.examples.semaphores.passingTheBaton.readersWriters;

import java.util.concurrent.Semaphore;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;

/** 
 * Global program state for readers/writers based on passing the baton technique. 
 * It has the reader's preference.
 * 
 * It is based on section 4.4.3 in the course book. 
 * 
 * @author Jonas Mellin
 *
 */

public class GlobalProgramState {
	public static Semaphore entry = new Semaphore(1);
	public static Semaphore delayedReader = new Semaphore(0);
	public static Semaphore delayedWriter = new Semaphore(0);
	
	public static int numberOfWriters=0;
	public static int numberOfReaders=0;
	public static int numberOfDelayedReaders=0;
	public static int numberOfDelayedWriters=0;
	public static int numberOfIterations=100;
	
	/**
	 *  The SIGNAL functionality encapstulated in a static void method in GlobalProgramState.
	 *  It encapsulates part of the reader preference semantics as well as provide a common
	 *  procedure that is executed to exist the critical section. Entering the critical section 
	 *  is more dependent on the type of the process, in this case, the reader and writer processes.
	 */

	public static void signal() {
		if (numberOfWriters == 0 && numberOfDelayedReaders>0) {
			--numberOfDelayedReaders;
			delayedReader.release();
		} else if ( numberOfReaders == 0 && numberOfWriters == 0 && numberOfDelayedWriters>0) {
			--numberOfDelayedWriters;
			delayedWriter.release();
		} else {
			entry.release();
		}
	}
	
	/**
	 * A support method to summarize the state of the synchronization.
	 * Note that it is not atomic and, thus, can be interleaved by other processes.
	 * @return
	 */
	public static String getState() {
		return String.format("nr = %d, nw = %d, dr = %d, dw = %d",numberOfReaders,numberOfWriters,numberOfDelayedReaders,numberOfDelayedWriters);
	}

	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());
		


		RunnableSpecification rs[]=new RunnableSpecification[2];
		AndrewsProcess[] process;
		try {
			// set up 5 readers and 2 writers
			rs[0]=new RunnableSpecification(Reader.class,5);
			rs[1]=new RunnableSpecification(Writer.class,2);
			// create the processes
			process = AndrewsProcess.andrewsProcessFactory(rs);
			// start the processes
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
