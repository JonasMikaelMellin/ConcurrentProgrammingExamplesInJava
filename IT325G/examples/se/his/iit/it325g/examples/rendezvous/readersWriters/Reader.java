package se.his.iit.it325g.examples.rendezvous.readersWriters;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;


public class Reader implements Runnable {

	private Random r;
	public Reader() {
	}

	@Override
	public void run() {
		r=new Random(AndrewsProcess.currentAndrewsProcessId());

		for (int i=0; i<GlobalProgramState.numberOfIterations; ++i) {
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" wants to read");
			GlobalProgramState.readerWriterAccess.acquireRead();
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" reads");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt()%1000));
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" wants to yodel");
			GlobalProgramState.readerWriterAccess.releaseRead();
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" is yodeling");
			AndrewsProcess.uninterruptibleMinimumDelay(Math.abs(r.nextInt()%1000));

		}
		
	}

}
