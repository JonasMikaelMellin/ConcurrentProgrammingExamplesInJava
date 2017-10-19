package se.his.iit.it325g.examples.messagePassing.peerValueExchange.centralized;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

public class PeerZeroRunnable extends PeerRunnable  {

	private int smallest,largest;
	public PeerZeroRunnable() {
		super();
		this.smallest=this.largest=this.getV();
	}

	@Override
	public void run() {
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" initial value ="+this.getV());
		for (int i=1; i<GlobalProgramState.numberOfPeers; ++i) {
			Integer newValue=GlobalProgramState.values.receive();
			smallest=(newValue<smallest?newValue:smallest);
			largest=(newValue>largest?newValue:largest);
			
		}
		for (int i=1; i<GlobalProgramState.numberOfPeers; ++i) {
			GlobalProgramState.result.get(i).send(new SmallestAndLargestValue(smallest,largest));
		}


		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" smallest ="+smallest+", and largest = "+largest);

	}

}
