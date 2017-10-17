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

package se.his.iit.it325g.semaphores.multipleProducerConsumerBoundedBuffer;

import se.his.iit.it325g.common.AndrewsProcess;

public class Producer implements Runnable {


	@Override
	public void run() {
		int i=1;
		while(true) {
			GlobalProgramState.empty.acquireUninterruptibly();
			System.out.println(AndrewsProcess.currentAndrewsProcessId()+" producing "+i);
			GlobalProgramState.buffer[GlobalProgramState.rear]=i++;
			GlobalProgramState.rear=(GlobalProgramState.rear+1)%GlobalProgramState.n;
			GlobalProgramState.full.release();
		}
	}

}
