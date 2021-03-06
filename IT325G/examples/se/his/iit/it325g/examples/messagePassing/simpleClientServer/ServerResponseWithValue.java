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

package se.his.iit.it325g.examples.messagePassing.simpleClientServer;

public class ServerResponseWithValue extends ServerResponse {
	private int value;

	public ServerResponseWithValue(int value) {
		super(true);
		this.value=value;
	}

	public ServerResponseWithValue(Exception exception) {
		super(exception);
	}
	public int  getValue() {
		return this.value;
	}

}
