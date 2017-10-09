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

package se.his.iit.it325g.allocatorClientServer;



import java.util.Vector;

import se.his.iit.it325g.common.AndrewsChan;
import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;
import se.his.iit.it325g.common.Char;

public class GlobalProgramState {
	public static final int numberOfResources = 100;
	public static Vector<AndrewsChan<ServerResponse>> reply=new Vector<AndrewsChan<ServerResponse>>();
	public static AndrewsChan<ClientRequest> request=new AndrewsChan<ClientRequest>();
	public static int numberOfIterations=100;
	public static int numberOfClients=10;
	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());

		for (int i=0; i<GlobalProgramState.numberOfClients; ++i) {
			reply.addElement(new AndrewsChan<ServerResponse>());
		}
		RunnableSpecification rs[]=new RunnableSpecification[2];
		rs[0]=new RunnableSpecification(Client.class,GlobalProgramState.numberOfClients);
		rs[1]=new RunnableSpecification(Server.class,1);
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
