package se.his.iit.it325g.examples.messagePassing.peerValueExchange.ring;



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

		GlobalProgramState.values.get(1).send(new SmallestAndLargestValue(smallest, largest));
		SmallestAndLargestValue salv=GlobalProgramState.values.get(0).receive();
		GlobalProgramState.values.get(1).send(salv);
		
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" smallest ="+salv.getSmallest()+", and largest = "+salv.getLargest());

	}

}
