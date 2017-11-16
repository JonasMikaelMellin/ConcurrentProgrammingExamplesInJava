package se.his.iit.it325g.examples.messagePassing.asynchMessExchange;



import se.his.iit.it325g.common.AndrewsProcess;

public class P1  implements Runnable {


	@Override
	public void run() {
		int value1=1,value2;
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" sending value ="+value1);

		GlobalProgramState.in1.send(value1);
		value2=(int) GlobalProgramState.in2.receive();
		
		System.out.println("Peer "+AndrewsProcess.currentAndrewsProcessId()+" recevied value = "+value2);

	}

}
