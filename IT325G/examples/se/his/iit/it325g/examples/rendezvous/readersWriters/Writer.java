package se.his.iit.it325g.examples.rendezvous.readersWriters;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;


public class Writer implements Runnable {

	private Random r;
	public Writer() {
	}

	@Override
	public void run() {
		r=new Random(AndrewsProcess.currentAndrewsProcessId());

		for (int i=0; i<GlobalProgramState.numberOfIterations; ++i) {
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" wants to write");
			GlobalProgramState.readerWriterAccess.acquireWrite();
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" is writing");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt()%1000));
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" wants to backfire");
			GlobalProgramState.readerWriterAccess.releaseWrite();
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" is backfiring.....");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt()%1000));

		}
		
	}

}
