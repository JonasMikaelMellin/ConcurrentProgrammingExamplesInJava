package se.his.iit.it325g.examples.messagePassing.peerValueExchange.symmetric;



import se.his.iit.it325g.common.AndrewsProcess;

public class PeerRunnable implements Runnable {
	
	private int v;
	private int smallest,largest;
	public PeerRunnable() {
		this.v=GlobalProgramState.random.nextInt();

	}

	@Override
	public void run() {
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" initial value ="+this.getV());
		for (int j=0; j<GlobalProgramState.numberOfPeers; ++j) {
			if (j==AndrewsProcess.currentAndrewsProcessId()) {
				continue;
			}
			GlobalProgramState.values.get(j).send(this.v);			
		}
		smallest=largest=this.v;
		for (int j=0; j<GlobalProgramState.numberOfPeers-1; ++j) {
			Integer newValue=GlobalProgramState.values.get(AndrewsProcess.currentAndrewsProcessId()).receive();
			smallest=(newValue<smallest?newValue:smallest);
			largest=(newValue>largest?newValue:largest);
			
		}
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" smallest ="+smallest+", and largest = "+largest);

	}

	/**
	 * @return the v
	 */
	public synchronized final int getV() {
		return v;
	}
	

}
