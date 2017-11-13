package se.his.iit.it325g.examples.messagePassing.peerValueExchange.centralized;



import se.his.iit.it325g.common.AndrewsProcess;

public class PeerRunnable implements Runnable {
	
	private int v;
	public PeerRunnable() {
		this.v=GlobalProgramState.random.nextInt();

	}

	@Override
	public void run() {
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" initial value ="+this.getV());

		GlobalProgramState.values.send(this.v);			
		SmallestAndLargestValue salv=GlobalProgramState.result.get(AndrewsProcess.currentAndrewsProcessId()).receive();
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" smallest ="+salv.getSmallest()+", and largest = "+salv.getLargest());

	}

	/**
	 * @return the v
	 */
	public synchronized final int getV() {
		return v;
	}
	

}
