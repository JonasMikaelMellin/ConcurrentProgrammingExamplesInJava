package se.his.iit.it325g.simpleClientServer;

import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

public class ClientSimulation implements Runnable {
	private int clientIdChannel;
	public ClientSimulation() {
	}

	private class RandomClientRequest {
		public int number;
		public ClientRequest clientRequest;
	}
	
	private RandomClientRequest generateRandomClientRequest(Random r) {
		RandomClientRequest randomClientRequest=new RandomClientRequest();
		randomClientRequest.number=Math.abs(r.nextInt()%3);
		switch (randomClientRequest.number) {
		case 0:
			randomClientRequest.clientRequest=new ClientRequestGetServerValue(AndrewsProcess.currentRelativeToTypeAndrewsProcessId());
			break;
		case 1:
			randomClientRequest.clientRequest=new ClientRequestAddValue(AndrewsProcess.currentRelativeToTypeAndrewsProcessId(),r.nextInt());
			break;
		case 2:
			randomClientRequest.clientRequest=new ClientRequestSubtractValue(AndrewsProcess.currentRelativeToTypeAndrewsProcessId(),r.nextInt());
			break;
		default:
			throw new IllegalStateException("We should not be in this state, something severe happened");
		}
		return randomClientRequest;
		
	}

	@Override
	public void run() {
		// get the identity relative to specific type of process
		this.clientIdChannel=AndrewsProcess.currentRelativeToTypeAndrewsProcessId();
		Random r=new Random(this.clientIdChannel);
		for (int i=0; i<10; ++i) {
			final RandomClientRequest randomClientRequest=generateRandomClientRequest(r);
			final ClientRequest clientRequest=randomClientRequest.clientRequest;
			System.out.println("Client "+AndrewsProcess.currentAndrewsProcessId()+" with relative identity "+AndrewsProcess.currentRelativeToTypeAndrewsProcessId()+" w.r.t. specific type "+this.getClass().getName()+"\n\tSends request "+clientRequest.getClass().getName());
			GlobalProgramState.request.send(clientRequest);
			ServerResponse serverResponse=GlobalProgramState.reply.get(AndrewsProcess.currentRelativeToTypeAndrewsProcessId()).receive();
			if (serverResponse.isSuccess()) {
				switch(randomClientRequest.number) {
				case 0:
					System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" Request resulted in "+((ServerResponseWithValue)serverResponse).getValue());
					break;
				case 1: case 2:
					System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" Request completed with success="+((ServerResponse)serverResponse).isSuccess());
					break;
				default:
					throw new IllegalStateException("We should not be in this state, something severe happened");

				}
			} else {
				System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" Client request is unsuccessful");
				serverResponse.getException().printStackTrace();
			}
			
		}
		ClientRequest closeSession=new ClientRequestCloseSession(this.clientIdChannel);
		GlobalProgramState.request.send(closeSession);
		ServerResponse closeSessionServerResponse=GlobalProgramState.reply.get(AndrewsProcess.currentRelativeToTypeAndrewsProcessId()).receive();

	}

}
