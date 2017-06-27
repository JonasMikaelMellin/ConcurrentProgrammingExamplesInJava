package se.his.iit.it325g.multipleProducerConsumerBoundedBuffer;

public class Consumer implements Runnable {


	@Override
	public void run() {
		while(true) {
			GlobalProgramState.full.acquireUninterruptibly();
			int value=GlobalProgramState.buffer[GlobalProgramState.front];
			GlobalProgramState.front=(GlobalProgramState.front+1)%GlobalProgramState.n;
			System.out.println("Consumer consumer value "+value);
			GlobalProgramState.empty.release();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

		}
	}

}
