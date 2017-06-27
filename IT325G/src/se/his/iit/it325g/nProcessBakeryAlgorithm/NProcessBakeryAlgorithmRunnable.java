package se.his.iit.it325g.nProcessBakeryAlgorithm;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

import se.his.iit.it325g.common.AndrewsProcess;


/**
 * @author melj
 * 
 * Java implementation of Tie breaker process solution from "Concurrent Programming" by Gregory andrews.
 * 
 * 
 *
 */
public class NProcessBakeryAlgorithmRunnable implements Runnable {
	
	@Override
	public void run() {
		final int i=AndrewsProcess.currentAndrewsProcessId();
		while (true) {
			GlobalProgramState.turn[i]=0;
			GlobalProgramState.turn[i]=Arrays.stream(GlobalProgramState.turn).reduce(-1, (a,b) -> Integer.max(a,b))+1;
			System.out.println("Thread "+i+" is at stage "+GlobalProgramState.turn[i]);
			for (int j=0; j<GlobalProgramState.turn.length; ++j) { 
				if (j==i) continue;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
				}
				while (GlobalProgramState.turn[j]!=-1 && (GlobalProgramState.turn[i]>GlobalProgramState.turn[j] ||(GlobalProgramState.turn[i]==GlobalProgramState.turn[j])&& i>j)) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is in critical section");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			GlobalProgramState.turn[i]=-1;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			System.out.println("Thread "+AndrewsProcess.currentAndrewsProcessId()+" is not in critical section");
		}
	}
	

}
