package se.his.iit.it325g.producerConsumerBoundedBuffer;

import se.his.iit.it325g.common.AndrewsProcess;

public class Producer implements Runnable {


	@Override
	public void run() {
		int i=1;
		while(true) {
			GlobalState.empty.acquireUninterruptibly();
			System.out.println(AndrewsProcess.currentAndrewsProcessId()+" producing "+i);
			GlobalState.buffer[GlobalState.rear]=i++;
			GlobalState.rear=(GlobalState.rear+1)%GlobalState.n;
			GlobalState.full.release();
		}
	}

}
