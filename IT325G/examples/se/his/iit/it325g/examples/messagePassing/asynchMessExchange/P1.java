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

package se.his.iit.it325g.examples.messagePassing.asynchMessExchange;



import se.his.iit.it325g.common.AndrewsProcess;

public class P1  implements Runnable {


	@Override
	public void run() {
		int value1=1,value2;
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" sending value ="+value1);

		GlobalProgramState.in1.send(value1);
		value2=(int) GlobalProgramState.in2.receive();
		
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" recevied value = "+value2);

	}

}
