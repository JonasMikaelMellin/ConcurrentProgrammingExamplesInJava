package se.his.iit.it325g.producerConsumerBoundedBuffer;

import java.util.Arrays;

import se.his.iit.it325g.common.AndrewsProcess;

public class Consumer implements Runnable {


	@Override
	public void run() {
		while(true) {
			GlobalProgramState.full.acquireUninterruptibly();
			GlobalProgramState.mutexF.acquireUninterruptibly();
			int value=GlobalProgramState.buffer[GlobalProgramState.front];
			GlobalProgramState.front=(GlobalProgramState.front+1)%GlobalProgramState.n;
			
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" consumes value "+value);
			System.out.println("\tBuffer content: "+Arrays.toString(GlobalProgramState.buffer));
			System.out.println("\tRear="+GlobalProgramState.rear+" front="+GlobalProgramState.front);
			GlobalProgramState.mutexF.release();
			GlobalProgramState.empty.release();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

		}
	}

}
