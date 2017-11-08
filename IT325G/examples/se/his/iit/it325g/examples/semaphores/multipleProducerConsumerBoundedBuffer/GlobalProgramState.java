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

package se.his.iit.it325g.examples.semaphores.multipleProducerConsumerBoundedBuffer;


import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;
import se.his.iit.it325g.common.AndrewsSemaphore;

/**
 * GlobalProgramState for the multiple producer/consumer problem with
 * a circular buffer. 
 * @author melj
 *
 */

public class GlobalProgramState {
	public static int n=10;
	public static int buffer[]=new int[n];
	public static int front=0;
	public static int rear=0;
	public static AndrewsSemaphore empty=new AndrewsSemaphore(n);
	public static AndrewsSemaphore full=new AndrewsSemaphore(0);
	public static AndrewsSemaphore mutexP=new AndrewsSemaphore(1);
	public static AndrewsSemaphore mutexC=new AndrewsSemaphore(1);

	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());
		
		RunnableSpecification rs[]=new RunnableSpecification[2];
		rs[0]=new RunnableSpecification(Producer.class,10);
		rs[1]=new RunnableSpecification(Consumer.class,10);
		try {
			AndrewsProcess process[]=AndrewsProcess.andrewsProcessFactory(rs);
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
