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

// This is related to figure 7.1 in the book


package se.his.iit.it325g.examples.messagePassing.filterProcessAssemblingLinesOfCharacters;

public class FilterConsumer implements Runnable {

	public FilterConsumer() {
	}

	@Override
	public void run() {
		String line=new String();
		while (true) {
			line=GlobalProgramState.output.receive();
			System.out.println(line);
		}
	}

}
