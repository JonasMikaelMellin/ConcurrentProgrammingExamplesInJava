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

package se.his.iit.it325g.rendezvous.criticalSection;



import se.his.iit.it325g.common.AsynchronousChan;
import se.his.iit.it325g.common.Chan;
import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;
import se.his.iit.it325g.common.rendezvous.Entry;
import se.his.iit.it325g.common.Char;

public class GlobalProgramState {
	public static final int numberOfClients = 3;
	public static AndrewsProcess criticalSectionServer=null;
	public static Runnable criticalSectionServerRunnable=null;
	public static int numberOfIterations=10;
	public static Entry enterCriticalSectionEntry;
	public static Entry exitCriticalSectionEntry;
	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());


		RunnableSpecification rs[]=new RunnableSpecification[2];
		rs[0]=new RunnableSpecification(CriticalSectionServer.class,1);
		rs[1]=new RunnableSpecification(ClientSimulation.class,numberOfClients);
		try {
			AndrewsProcess process[]=AndrewsProcess.andrewsProcessFactory(rs);
			GlobalProgramState.criticalSectionServer=process[0];
			GlobalProgramState.criticalSectionServerRunnable=process[0].getRunnable();
			enterCriticalSectionEntry=((CriticalSectionServer)GlobalProgramState.criticalSectionServerRunnable).getEntry("enterCriticalSection");
			exitCriticalSectionEntry=((CriticalSectionServer)GlobalProgramState.criticalSectionServerRunnable).getEntry("exitCriticalSection");
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
