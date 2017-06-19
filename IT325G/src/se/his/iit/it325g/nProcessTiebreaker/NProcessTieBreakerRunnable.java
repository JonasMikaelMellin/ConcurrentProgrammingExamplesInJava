package se.his.iit.it325g.nProcessTiebreaker;

import se.his.iit.it325g.common.AndrewsProcess;


/**
 * @author melj
 * 
 * Java implementation of Tie breaker process solution from "Concurrent Programming" by Gregory andrews.
 * 
 * 
 *
 */
public class NProcessTieBreakerRunnable implements Runnable {
	static int n=10;
	static int in[]=new int[n],last[]=new int[n];
	
	@Override
	public void run() {
		while (true) {
			for (int j=0; j<in.length-1; ++j) {
				in[AndrewsProcess.currentAndrewsProcessId()]=j;
				last[j]=AndrewsProcess.currentAndrewsProcessId();
				System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is at stage "+j);
				for (int k=0; k<in.length; ++k) { 
					if (k==AndrewsProcess.currentAndrewsProcessId()) continue;
					while (in[k]>=in[AndrewsProcess.currentAndrewsProcessId()] && last[j]==AndrewsProcess.currentAndrewsProcessId()) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
						}
					}
				}
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is in critical section");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			in[AndrewsProcess.currentAndrewsProcessId()]=0;
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is not in critical section");
		}
	}
	
	public static void main(String argv[]) {
		AndrewsProcess[] process;
		try {
			process = AndrewsProcess.andrewsProcessFactory(n, NProcessTieBreakerRunnable.class);
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
