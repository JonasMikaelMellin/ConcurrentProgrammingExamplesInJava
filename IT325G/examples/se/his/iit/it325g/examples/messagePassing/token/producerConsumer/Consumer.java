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




package se.his.iit.it325g.examples.messagePassing.token.producerConsumer;

import se.his.iit.it325g.common.AndrewsProcess;

public class Consumer implements Runnable {
	private int token;
	@Override
	public void run() {
		token=AndrewsProcess.currentAndrewsProcessId()*1000;
		while (true) {
			int value=(int)GlobalProgramState.full.receive();
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" consumes value "+value);
			GlobalProgramState.empty.send(token++);
		}

	}

}
