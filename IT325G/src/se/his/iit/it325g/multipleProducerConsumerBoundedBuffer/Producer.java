package se.his.iit.it325g.multipleProducerConsumerBoundedBuffer;

import se.his.iit.it325g.common.AndrewsProcess;

public class Producer implements Runnable {


	@Override
	public void run() {
		int i=1;
		while(true) {
			GlobalProgramState.empty.acquireUninterruptibly();
			System.out.println(AndrewsProcess.currentAndrewsProcessId()+" producing "+i);
			GlobalProgramState.buffer[GlobalProgramState.rear]=i++;
			GlobalProgramState.rear=(GlobalProgramState.rear+1)%GlobalProgramState.n;
			GlobalProgramState.full.release();
		}
	}

}
