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

package se.his.iit.it325g.common;

import java.util.concurrent.LinkedBlockingQueue;

public class AsynchronousChan<T> extends Chan<T> {
	private LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<T>();

	public AsynchronousChan() {
	}

	@Override
	public synchronized void send(T value) {
		while (this.queue.remainingCapacity() <= 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// hide interrupts
			}
		}

		this.queue.add(value);
		this.notifyAll();
	}

	@Override
	public synchronized T receive() {

		// block until there is a result

		T result = this.queue.poll();
		while (result == null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// hide interrupts
			}
			result = this.queue.poll();
		}
		this.notifyAll();
		return result;
	}

}
