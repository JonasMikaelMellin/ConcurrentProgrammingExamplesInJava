package se.his.iit.it325g.examples.semaphores.passingTheBaton.readersWriters;

import java.util.Random;

/**
 * The writer process according to section 4.4.3.
 * @author Jonas Mellin
 */

import se.his.iit.it325g.common.AndrewsProcess;

public class Writer implements Runnable {

	public Writer() {
	}

	@Override
	public void run() {
		Random r=new Random(AndrewsProcess.currentAndrewsProcessId());
		
		for (int i=0; i<GlobalProgramState.numberOfIterations; ++i) {
			System.out.println("Writer "+AndrewsProcess.currentAndrewsProcessId()+" trying to enter critical section");
			GlobalProgramState.entry.acquireUninterruptibly();
			System.out.println("Writer "+AndrewsProcess.currentAndrewsProcessId()+" trying to enter critical section, state: "+GlobalProgramState.getState());
			if (GlobalProgramState.numberOfWriters>0 || GlobalProgramState.numberOfReaders>0) {
				++GlobalProgramState.numberOfDelayedWriters;
				System.out.println("Writer "+AndrewsProcess.currentAndrewsProcessId()+" delayed, state: "+GlobalProgramState.getState());
				GlobalProgramState.entry.release();
				GlobalProgramState.delayedWriter.acquireUninterruptibly();
				System.out.println("Writer "+AndrewsProcess.currentAndrewsProcessId()+" released, state: "+GlobalProgramState.getState());

			}
			++GlobalProgramState.numberOfWriters;
			System.out.println("Writer "+AndrewsProcess.currentAndrewsProcessId()+" entering critical section, state: "+GlobalProgramState.getState());
			GlobalProgramState.signal();
			
			System.out.println("Writer "+AndrewsProcess.currentAndrewsProcessId()+" in critical section");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(1000)));
			
			System.out.println("Writer "+AndrewsProcess.currentAndrewsProcessId()+" trying to exit critical section");
			GlobalProgramState.entry.acquireUninterruptibly();
			--GlobalProgramState.numberOfWriters;
			System.out.println("Writer "+AndrewsProcess.currentAndrewsProcessId()+" exiting critical section, state: "+GlobalProgramState.getState());
			GlobalProgramState.signal();
			System.out.println("Writer "+AndrewsProcess.currentAndrewsProcessId()+" exited");
	
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt(1000)));

		}

	}

}
