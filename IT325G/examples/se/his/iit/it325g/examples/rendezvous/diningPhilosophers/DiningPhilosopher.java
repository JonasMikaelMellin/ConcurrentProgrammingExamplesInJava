package se.his.iit.it325g.examples.rendezvous.diningPhilosophers;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;


public class DiningPhilosopher implements Runnable {

	private Random r;
	public DiningPhilosopher() {
	}

	@Override
	public void run() {
		r=new Random(AndrewsProcess.currentAndrewsProcessId());

		for (int i=0; i<GlobalProgramState.numberOfIterations; ++i) {
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" wants to eat");
			GlobalProgramState.eatEntry.call(AndrewsProcess.currentRelativeToTypeAndrewsProcessId());
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" eats");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt()%1000));
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" wants to think");
			GlobalProgramState.eatExit.call(AndrewsProcess.currentRelativeToTypeAndrewsProcessId());
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" thinks");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt()%1000));

		}
		
	}

}
