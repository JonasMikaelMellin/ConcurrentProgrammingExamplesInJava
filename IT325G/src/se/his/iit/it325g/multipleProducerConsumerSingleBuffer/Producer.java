package se.his.iit.it325g.multipleProducerConsumerSingleBuffer;

import se.his.iit.it325g.common.AndrewsProcess;

public class Producer implements Runnable {


	@Override
	public void run() {
		int i=1;
		while(true) {
			GlobalState.empty.acquireUninterruptibly();
			System.out.println(AndrewsProcess.currentAndrewsProcessId()+" producing "+i);
			GlobalState.buffer=i++;
			GlobalState.full.release();
		}
	}

}
