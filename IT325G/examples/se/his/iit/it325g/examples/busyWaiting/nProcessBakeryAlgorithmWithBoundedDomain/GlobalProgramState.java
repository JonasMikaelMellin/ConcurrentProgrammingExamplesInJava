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

package se.his.iit.it325g.examples.busyWaiting.nProcessBakeryAlgorithmWithBoundedDomain;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;

public class GlobalProgramState {
	static int n=10;
	static int turn[]=IntStream.generate(() -> -1).limit(n).toArray(); // defaults to n * -1
	
	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());

		RunnableSpecification rs[]=new RunnableSpecification[1];
		rs[0]=new RunnableSpecification(NProcessBakeryAlgorithmWithBoundedDomainRunnable.class,n);
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
