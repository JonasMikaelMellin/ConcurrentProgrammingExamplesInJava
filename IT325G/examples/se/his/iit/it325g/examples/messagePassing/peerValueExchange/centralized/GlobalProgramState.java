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

// based on figure 7.11

package se.his.iit.it325g.examples.messagePassing.peerValueExchange.centralized;



import java.util.Random;
import java.util.Vector;

import se.his.iit.it325g.common.AsynchronousChan;
import se.his.iit.it325g.common.Chan;
import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;
import se.his.iit.it325g.common.Char;

public class GlobalProgramState {
	public static Chan<Integer> values=new AsynchronousChan<Integer>();
	public static int numberOfPeers=10;
	public static Random random=new Random(1);
	public static Vector<Chan<SmallestAndLargestValue>> result=new Vector<Chan<SmallestAndLargestValue>>();
	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());
		
		// initialize the channels
		result.setSize(numberOfPeers);
		for (int i=1; i<GlobalProgramState.numberOfPeers; ++i) {
			result.setElementAt(new AsynchronousChan<SmallestAndLargestValue>(),i);
		}
		RunnableSpecification rs[]=new RunnableSpecification[2];
		rs[0]=new RunnableSpecification(PeerZeroRunnable.class,1);
		rs[1]=new RunnableSpecification(PeerRunnable.class,GlobalProgramState.numberOfPeers-1);
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
