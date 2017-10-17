package se.his.iit.it325g.semaphores.diningPhilosphers;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

public class PhilosopherLeftToRight implements Runnable {

	public PhilosopherLeftToRight() {
	}

	@Override
	public void run() {
		final int left=4;
		final int right=0;
		Random r=new Random(left);
		
		while (true) {

			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" is awating fork "+right+" and "+left);
			GlobalProgramState.fork[left].acquireUninterruptibly();
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" is awating fork "+left);
			GlobalProgramState.fork[right].acquireUninterruptibly();
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" is eating");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(1000)));
			GlobalProgramState.fork[right].release();
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" released fork "+right);
			GlobalProgramState.fork[left].release();
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" released fork "+left);
			System.out.println("Philosopher "+AndrewsProcess.currentAndrewsProcessId()+" is thinking");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(1000)));
		}

	}

}
