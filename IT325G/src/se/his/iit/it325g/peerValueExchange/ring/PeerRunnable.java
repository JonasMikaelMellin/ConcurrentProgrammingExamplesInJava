package se.his.iit.it325g.peerValueExchange.ring;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

public class PeerRunnable implements Runnable {
	
	private int v;

	public PeerRunnable() {
		this.v=GlobalProgramState.random.nextInt();

	}

	@Override
	public void run() {
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" initial value ="+this.getV());
		SmallestAndLargestValue salv=GlobalProgramState.values.get(AndrewsProcess.currentAndrewsProcessId()).receive();
		final int smallest=(v<salv.getSmallest()?v:salv.getSmallest());
		final int largest=(v>salv.getLargest()?v:salv.getLargest());
		GlobalProgramState.values.get((AndrewsProcess.currentAndrewsProcessId()+1)%GlobalProgramState.numberOfPeers).send(new SmallestAndLargestValue(smallest,largest));;
		SmallestAndLargestValue salv2=GlobalProgramState.values.get(AndrewsProcess.currentAndrewsProcessId()).receive();
		GlobalProgramState.values.get((AndrewsProcess.currentAndrewsProcessId()+1)%GlobalProgramState.numberOfPeers).send(salv2);
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" smallest ="+salv2.getSmallest()+", and largest = "+salv2.getLargest());

	}

	/**
	 * @return the v
	 */
	public synchronized final int getV() {
		return v;
	}
	

}
