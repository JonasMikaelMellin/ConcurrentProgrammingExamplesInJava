package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

import java.util.HashSet;
import java.util.Random;

import se.his.iit.it325g.common.AndrewsProcess;

public class ClientSimulation implements Runnable {
	private int clientIdChannel;
	private HashSet<Integer> allocatedResources=new HashSet<Integer>();
	public ClientSimulation() {
	}

	private class RandomClientRequest {
		public int number;
		public ClientRequest clientRequest;
	}
	
	private RandomClientRequest generateRandomClientRequest(Random r) {
		RandomClientRequest randomClientRequest=new RandomClientRequest();
		double sample=r.nextDouble();
		// sample from uniform distribution
		if (sample<0.49) {
			randomClientRequest.number=0;

		} else if (sample<0.99) {
			
			randomClientRequest.number=1;
		} else {
			randomClientRequest.number=2;
		}
			
		switch (randomClientRequest.number) {
		case 0:

			try {
				randomClientRequest.clientRequest=new ClientRequestRead(this.clientIdChannel,Math.abs(r.nextInt()%1024));
			} catch (IncorrectClientRequest e) {
				System.out.println("This should not happen");
				e.printStackTrace();
				System.exit(0);
			}
			break;
		case 1:
			try {
				randomClientRequest.clientRequest=new ClientRequestWrite(this.clientIdChannel,Math.abs(r.nextInt()%1024),r.nextInt());
			} catch (IncorrectClientRequest e) {
				System.out.println("This should not happen");
				e.printStackTrace();
				System.exit(0);
			}
			break;
		case 2:
			try {
				randomClientRequest.clientRequest=new ClientRequestCloseSession(this.clientIdChannel);
			} catch (IncorrectClientRequest e) {
				System.out.println("This should not happen");
				e.printStackTrace();
				System.exit(0);
				
			}
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
		for (int i=0; i<GlobalProgramState.numberOfIterations; ++i) {
			ClientRequestOpenSession clientRequest=null;
			try {
				clientRequest = new ClientRequestOpenSession(this.clientIdChannel);
			} catch (IncorrectClientRequest e) {
				System.err.println("Incorrect client request");
				e.printStackTrace();
				System.exit(0);

			}
			GlobalProgramState.openSession.send(clientRequest);
			ServerResponse serverResponse=GlobalProgramState.reply.get(this.clientIdChannel).receive();
			if (!serverResponse.isSuccess()) {
				System.err.println(serverResponse.getException().getMessage());
				System.exit(0);
			}
			boolean sessionIsOpen=true;
			while (sessionIsOpen) {
				final RandomClientRequest randomClientRequest=generateRandomClientRequest(r);
				final ClientRequestNotOpenSession crnos=(ClientRequestNotOpenSession)randomClientRequest.clientRequest;
				System.out.println("Client "+AndrewsProcess.currentAndrewsProcessId()+" with relative identity "+AndrewsProcess.currentRelativeToTypeAndrewsProcessId()+" w.r.t. specific type "+this.getClass().getName()+"\n\tSends request "+crnos.getClass().getName());
				GlobalProgramState.request.send(crnos);
				ServerResponse innerServerResponse=GlobalProgramState.reply.get(AndrewsProcess.currentRelativeToTypeAndrewsProcessId()).receive();
				ClientRequestIndex innerClientRequest=null;
				if (randomClientRequest.number!=2) {
					innerClientRequest=(ClientRequestIndex) crnos;
				}
				if (innerServerResponse.isSuccess()) {
					switch(randomClientRequest.number) {
					case 0:
						System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" Read request at index="+innerClientRequest.getIndex()+" resulted in value="+((ServerResponseInteger)innerServerResponse).getValue());
						break;
					case 1: 
						System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" Write request at index="+innerClientRequest.getIndex()+" of "+((ClientRequestWrite)innerClientRequest).getValue()+" completed with success="+innerServerResponse.isSuccess());
						break;
					case 2:
						System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" close session request completed with success="+innerServerResponse.isSuccess());
						sessionIsOpen=false;
						break;
					default:
						throw new IllegalStateException("We should not be in this state, something severe happened");

					}
				} else {
					System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" Client request is unsuccessful");
					innerServerResponse.getException().printStackTrace();
				}
			}
			
		}

	}

}
