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


// Based  on figure 7.2 in the book

package se.his.iit.it325g.examples.messagePassing.mergeSorter;

import se.his.iit.it325g.common.Chan;

public class Merge implements Runnable {
	private Chan<Integer> in1;
	private Chan<Integer> in2;
	private Chan<Integer> out;
	public Merge() {
		this.in1=GlobalProgramState.in.get(0);
		this.in2=GlobalProgramState.in.get(1);
		this.out=GlobalProgramState.out;
	}

	@Override
	public void run() {
		Integer v1,v2;
		v1=this.in1.receive();
		v2=this.in2.receive();
		while (v1!=GlobalProgramState.endOfStream && v2!=GlobalProgramState.endOfStream) {
			if (v1<=v2) {
				this.out.send(v1);
				v1=this.in1.receive();
			} else {
				this.out.send(v2);
				v2=this.in2.receive();
			}
		}
		if (v1==GlobalProgramState.endOfStream) {
			while (v2!=GlobalProgramState.endOfStream) {
				this.out.send(v2);
				v2=this.in2.receive();
			}
		} else {
			while (v1!=GlobalProgramState.endOfStream) {
				this.out.send(v1);
				v1=this.in1.receive();
			}
		}
		this.out.send(GlobalProgramState.endOfStream);
		
	}

}
