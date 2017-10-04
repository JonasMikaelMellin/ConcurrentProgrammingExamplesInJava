package se.his.iit.it325g.common;

import java.util.concurrent.LinkedBlockingQueue;

public class AndrewsChan<T> {
	private LinkedBlockingQueue<T> queue=new LinkedBlockingQueue<T>();

	public AndrewsChan() {
	}
	
	public synchronized void send(T value) {
		while(this.queue.remainingCapacity()<=0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// hide interrupts				
			}
		}
		
		this.queue.add(value);
		this.notifyAll();
	}
	public synchronized T receive() {
		
		// block until there is a result
		
		T result=this.queue.poll();
		while (result==null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// hide interrupts
			}
			result=this.queue.poll();
		}
		return result;
	}
	

}
