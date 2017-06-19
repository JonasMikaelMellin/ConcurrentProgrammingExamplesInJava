package se.his.iit.it325g.twoProcessTieBreaker;

import se.his.iit.it325g.common.AndrewsProcess;


/**
 * @author melj
 * 
 * Java implementation of Tie breaker process solution from "Concurrent Programming" by Gregory andrews.
 * 
 * 
 *
 */
public class TwoProcessTieBreakerRunnable1 implements Runnable {

	
	@Override
	public void run() {
		while (true) {
			GlobalState.in1=true;
			GlobalState.last=1;
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is waiting to access critical section");
			while (GlobalState.in2 && GlobalState.last==1) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is in critical section");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			GlobalState.in1=false;
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is not in critical section");
		}
	}
	

}
