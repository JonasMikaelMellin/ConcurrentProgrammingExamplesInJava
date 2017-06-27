package se.his.iit.it325g.producerConsumerBoundedBuffer;

import java.util.Arrays;

import se.his.iit.it325g.common.AndrewsProcess;

public class Producer implements Runnable {


	@Override
	public void run() {
		int i=1;
		while(true) {
			GlobalProgramState.empty.acquireUninterruptibly();
			GlobalProgramState.mutexD.acquireUninterruptibly();
			GlobalProgramState.buffer[GlobalProgramState.rear]=i;
			GlobalProgramState.rear=(GlobalProgramState.rear+1)%GlobalProgramState.n;
			System.out.println("Process "+AndrewsProcess.currentAndrewsProcessId()+" producing "+i);
			System.out.println("Buffer content: "+Arrays.toString(GlobalProgramState.buffer));
			System.out.println("\tRear="+GlobalProgramState.rear+" front="+GlobalProgramState.front);
			i++;
			GlobalProgramState.mutexD.release();
			GlobalProgramState.full.release();
		}
	}

}
