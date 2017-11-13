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

import java.util.LinkedList;
import java.util.Queue;


public class SynchronousChan<T> extends Chan<T> {
	private T value;
	private boolean senderEntered = false, receiverEntered = false,
			senderExiting = false, receiverExiting = false;
	private Queue<AndrewsProcess> waitingSenders = new LinkedList<AndrewsProcess>();
	private Queue<AndrewsProcess> waitingReceivers = new LinkedList<AndrewsProcess>();

	public SynchronousChan() {
	}

	@Override
	public synchronized void send(T value) {
		if (this.senderEntered) {
			this.waitingSenders.add((AndrewsProcess) Thread.currentThread());
		}
		while (this.senderEntered
				|| (!this.senderEntered && !((AndrewsProcess) Thread
						.currentThread()).equals(this.waitingSenders.peek()))) {
			try {
				this.wait();
			} catch (InterruptedException interruptedException) {
				AndrewsProcess.defaultInterruptedExceptionHandling(interruptedException);
			}
		}
		if (((AndrewsProcess) Thread.currentThread())
				.equals(this.waitingSenders.peek())) {
			this.waitingSenders.poll();
		}
		this.senderEntered = true;
		this.value = value;
		this.notifyAll();
		while (!this.receiverEntered) {
			try {
				this.wait();
			} catch (InterruptedException interruptedException) {
				AndrewsProcess.defaultInterruptedExceptionHandling(interruptedException);
			}
		}
		this.senderExiting = true;
		this.notifyAll();
		while (!this.receiverExiting) {
			try {
				this.wait();
			} catch (InterruptedException interruptedException) {
				AndrewsProcess.defaultInterruptedExceptionHandling(interruptedException);
			}
		}
		this.senderExiting = false;
		this.senderEntered = false;
		this.notifyAll();
	}

	@Override
	public synchronized T receive() {

		T result;
		if (this.receiverEntered) {
			this.waitingReceivers.add((AndrewsProcess) Thread.currentThread());
		}
		while (this.receiverEntered
				|| (!this.receiverEntered && !((AndrewsProcess) Thread
						.currentThread()).equals(this.waitingReceivers.peek()))) {
			try {
				this.wait();
			} catch (InterruptedException interruptedException) {
				AndrewsProcess.defaultInterruptedExceptionHandling(interruptedException);
			}
		}
		if (((AndrewsProcess) Thread.currentThread())
				.equals(this.waitingReceivers.peek())) {
			this.waitingReceivers.poll();
		}
		this.receiverEntered = true;
		while (!this.senderEntered) {
			try {
				this.wait();
			} catch (InterruptedException interruptedException) {
				AndrewsProcess.defaultInterruptedExceptionHandling(interruptedException);
			}
		}
		result = this.value;
		this.receiverExiting = true;
		while (!this.senderExiting) {
			try {
				this.wait();
			} catch (InterruptedException interruptedException) {
				AndrewsProcess.defaultInterruptedExceptionHandling(interruptedException);
			}
		}
		this.receiverExiting = false;
		this.receiverEntered = false;

		this.notifyAll();
		return result;
	}

}
