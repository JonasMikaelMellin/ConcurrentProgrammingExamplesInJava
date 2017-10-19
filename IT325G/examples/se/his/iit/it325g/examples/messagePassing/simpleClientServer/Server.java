package se.his.iit.it325g.examples.messagePassing.simpleClientServer;

import se.his.iit.it325g.common.AndrewsProcess;

public class Server implements Runnable {
	
	private int serverValue=0;
	private int numberOfSessions=2;

	public Server() {
	}

	@Override
	public void run() {
		while (true) {
			final ClientRequest clientRequest=GlobalProgramState.request.receive();
			ServerResponse serverResponse=null;
			switch(clientRequest.getOperation()) {
			case add: case subtract:
				try {
					this.serverValue=((ClientRequestArithmeticOperator)clientRequest).performOperation(this.serverValue);
					serverResponse=new ServerResponse(true);
				}
				catch (Exception e) {
					serverResponse=new ServerResponse(e);
				}
				break;
			case getServerValue:
				try {
					serverResponse=new ServerResponseWithValue(this.serverValue);
				}
				catch (Exception e) {
					serverResponse=new ServerResponse(e);
				}
				break;
			case closeSession:
				--this.numberOfSessions;
				serverResponse=new ServerResponse(true);
				break;
			default:
				break;
			
			}
			// send response back on client's reply channel, note that
			// client id is relative to the type not relative to all processes
			GlobalProgramState.reply.get(clientRequest.getClientId()).send(serverResponse);
			if (this.numberOfSessions<=0) {
				AndrewsProcess.uninterruptibleMinimumDelay(100);
				System.out.println("Final session closed, terminating");
				System.exit(0);
			}
		}
	}

}
