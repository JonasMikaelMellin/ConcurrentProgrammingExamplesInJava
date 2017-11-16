package se.his.iit.it325g.examples.messagePassing.asynchMessExchange;



import se.his.iit.it325g.common.AndrewsProcess;

public class P2 implements Runnable {
	

	public P2() {

	}

	@Override
	public void run() {
		int value1, value2=2;
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" receiveing value");
		value1=(int)GlobalProgramState.in1.receive();
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" received value ="+value1+" sending value="+value2);
		GlobalProgramState.in2.send(value2);
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" send value="+value2);

	}


	

}
