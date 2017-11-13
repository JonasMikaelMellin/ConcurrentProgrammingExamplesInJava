package se.his.iit.it325g.examples.monitors.shortestJobNext.targetedNotify;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

public class ClientSimulation implements Runnable {

	@Override
	public void run() {
		Random r=new Random(AndrewsProcess.currentAndrewsProcessId());
		while (true) {
			int t=Math.abs(r.nextInt(100));
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" waiting at "+t);
			GlobalProgramState.shortestJobNextMonitor.request(t);
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" granted");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(100)));              
			GlobalProgramState.shortestJobNextMonitor.release();
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" released");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(100)));
			
		}

	}

}
