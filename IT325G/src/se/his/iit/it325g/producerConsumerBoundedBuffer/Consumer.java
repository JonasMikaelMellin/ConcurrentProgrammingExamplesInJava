package se.his.iit.it325g.producerConsumerBoundedBuffer;

public class Consumer implements Runnable {


	@Override
	public void run() {
		while(true) {
			GlobalState.full.acquireUninterruptibly();
			int value=GlobalState.buffer[GlobalState.front];
			GlobalState.front=(GlobalState.front+1)%GlobalState.n;
			System.out.println("Consumer consumer value "+value);
			GlobalState.empty.release();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

		}
	}

}
