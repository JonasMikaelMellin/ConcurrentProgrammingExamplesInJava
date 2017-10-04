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

// This is related to figure 7.2 in the book

package se.his.iit.it325g.mergeSorter;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.se.iit.it325g.common.exception.AndrewsProcessNotYetInitialized;

public class IntegerSource implements Runnable {
	private int n=10;
	private int inIndex=-1;
	public IntegerSource() {
	}

	@Override
	public void run() {

		this.inIndex=AndrewsProcess.currentAndrewsProcessId()%2;

		Random r=new Random(this.inIndex);
		List<Integer> sourceList=r.ints(n,-100,100).sorted().boxed().collect(Collectors.toList());

		for (Integer value: sourceList) {
			GlobalProgramState.in.get(this.inIndex).send(value);
		}

		GlobalProgramState.in.get(this.inIndex).send(GlobalProgramState.endOfStream);
		
	}

}
