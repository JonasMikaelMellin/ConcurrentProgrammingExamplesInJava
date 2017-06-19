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
public class TwoProcessTieBreakerRunnable2 implements Runnable {

	
	@Override
	public void run() {
		while (true) {
			GlobalState.in2=true;
			GlobalState.last=2;
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is waiting to access critical section");
			while (GlobalState.in1 && GlobalState.last==2) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is in critical section");
			GlobalState.in2=false;
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is not in critical section");
		}
	}
	

}
