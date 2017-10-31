package se.his.iit.it325g.examples.semaphores.passingTheBaton.readersWriters;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

/**
 * The reader process type as specified in section 4.4.3.
 * @author Jonas Mellin
 *
 */
public class Reader implements Runnable {

	public Reader() {
	}

	@Override
	public void run() {
		Random r=new Random(AndrewsProcess.currentAndrewsProcessId());
		
		for (int i=0; i<GlobalProgramState.numberOfIterations; ++i) {
			System.out.println("Reader "+AndrewsProcess.currentAndrewsProcessId()+" trying to enter critical section");
			GlobalProgramState.entry.P();
			System.out.println("Reader "+AndrewsProcess.currentAndrewsProcessId()+" trying to enter critical section, state: "+GlobalProgramState.getState());
			if (GlobalProgramState.numberOfWriters>0) {
				++GlobalProgramState.numberOfDelayedReaders;
				System.out.println("Reader "+AndrewsProcess.currentAndrewsProcessId()+" delayed");
				GlobalProgramState.entry.V();
				GlobalProgramState.delayedReader.P();
				System.out.println("Reader "+AndrewsProcess.currentAndrewsProcessId()+" released, state: "+GlobalProgramState.getState());

			}
			++GlobalProgramState.numberOfReaders;
			System.out.println("Reader "+AndrewsProcess.currentAndrewsProcessId()+"  entering critical section, state: "+GlobalProgramState.getState());
			GlobalProgramState.signal();
			
			System.out.println("Reader "+AndrewsProcess.currentAndrewsProcessId()+" in critical section");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(1000)));
			
			System.out.println("Reader "+AndrewsProcess.currentAndrewsProcessId()+" exiting critical section, state: "+GlobalProgramState.getState());
			GlobalProgramState.entry.P();
			--GlobalProgramState.numberOfReaders;
			GlobalProgramState.signal();
			System.out.println("Reader "+AndrewsProcess.currentAndrewsProcessId()+" exited");
	
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(1000)));

		}

	}

}
