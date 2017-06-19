package se.his.iit.it325g.multipleProducerConsumerSingleBuffer;

public class Consumer implements Runnable {


	@Override
	public void run() {
		while(true) {
			GlobalState.full.acquireUninterruptibly();
			int value=GlobalState.buffer;
			System.out.println("Consumer consumer value "+value);
			GlobalState.empty.release();

		}
	}

}
