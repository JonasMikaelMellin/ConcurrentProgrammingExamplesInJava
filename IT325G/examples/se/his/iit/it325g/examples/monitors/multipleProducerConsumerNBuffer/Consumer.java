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

package se.his.iit.it325g.examples.monitors.multipleProducerConsumerNBuffer;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

public class Consumer implements Runnable {


	@Override
	public void run() {
		Random r=new Random(AndrewsProcess.currentAndrewsProcessId());
		while(true) {
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+": fetching value ");
			int value=GlobalProgramState.buffer.fetch();
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+": consuming value "+value);
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt()%100));

		}
	}

}
