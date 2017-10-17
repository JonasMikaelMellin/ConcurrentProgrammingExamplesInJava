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

package se.his.iit.it325g.mergeSorter;



import java.util.Vector;

import se.his.iit.it325g.common.AsynchronousChan;
import se.his.iit.it325g.common.Chan;
import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;
import se.his.iit.it325g.common.Char;

public class GlobalProgramState {
	public static Vector<Chan<Integer>> in=new Vector<Chan<Integer>>();
	public static Chan<Integer> out=new AsynchronousChan<Integer>();
	public static Integer endOfStream=Integer.MIN_VALUE; // the sentinel should not be part of the domain, but that complicates the solution
	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());

		for (int i=0; i<2; ++i) {
			in.addElement(new AsynchronousChan<Integer>());
		}
		RunnableSpecification rs[]=new RunnableSpecification[3];
		rs[0]=new RunnableSpecification(IntegerSource.class,2);
		rs[1]=new RunnableSpecification(Merge.class,1);
		rs[2]=new RunnableSpecification(IntegerSink.class,1);
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
