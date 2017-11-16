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


package se.his.iit.it325g.examples.messagePassing.synchMessExchange;



import se.his.iit.it325g.common.AndrewsProcess;

public class P2 implements Runnable {
	

	public P2() {

	}

	@Override
	public void run() {
		int value1, value2=2;
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" receiveing value");
		value1=(int)GlobalProgramState.in1.receive();
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" received value ="+value1+" sending value="+value2);
		GlobalProgramState.in2.send(value2);
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" send value="+value2);

	}


	

}
