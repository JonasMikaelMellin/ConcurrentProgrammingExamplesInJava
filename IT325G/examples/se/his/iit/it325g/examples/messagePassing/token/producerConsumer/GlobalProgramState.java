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

package se.his.iit.it325g.examples.messagePassing.token.producerConsumer;



import java.util.Random;
import java.util.Vector;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;
import se.his.iit.it325g.common.AsynchronousChan;


public class GlobalProgramState {
	public volatile static AsynchronousChan<Integer> full = new AsynchronousChan<Integer>();
	public volatile static AsynchronousChan<Integer> empty = new AsynchronousChan<Integer>();
	public  static final int n=5;
	public volatile static Random random=new Random(1);
	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());
		

		// note that the ordering of the specification is critical
		// since it maintains implicit assumptions in the program
		// a more robust implementation is judged to be less
		// understandable
		RunnableSpecification rs[]=new RunnableSpecification[3];
		rs[0]=new RunnableSpecification(Producer.class,n);
		rs[1]=new RunnableSpecification(Consumer.class,n);
		rs[2]=new RunnableSpecification(Initiator.class,1);
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
