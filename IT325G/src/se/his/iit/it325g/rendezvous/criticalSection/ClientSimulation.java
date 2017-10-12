package se.his.iit.it325g.rendezvous.criticalSection;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.rendezvous.Entry;

public class ClientSimulation implements Runnable {

	private Random r;
	public ClientSimulation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		r=new Random(AndrewsProcess.currentAndrewsProcessId());

		for (int i=0; i<GlobalProgramState.numberOfIterations; ++i) {
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" is entering the critical section");
			GlobalProgramState.enterCriticalSectionEntry.call();
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" has entered the critical section");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt()%1000));
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" is exiting the critical section");
			GlobalProgramState.exitCriticalSectionEntry.call();
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" has exited the critical section");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt()%1000));

		}
		
	}

}
