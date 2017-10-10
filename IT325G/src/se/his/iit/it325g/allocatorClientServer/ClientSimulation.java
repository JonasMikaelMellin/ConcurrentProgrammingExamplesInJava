package se.his.iit.it325g.allocatorClientServer;

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
		if (r.nextDouble()>allocatedResources.size()/(2.0+allocatedResources.size())) {
			randomClientRequest.number=0;

		} else {
			randomClientRequest.number=1;
		}
			
		switch (randomClientRequest.number) {
		case 0:

			try {
				randomClientRequest.clientRequest=new ClientRequestAcquireResource(AndrewsProcess.currentRelativeToTypeAndrewsProcessId());
			} catch (IncorrectResourceRequest e) {
				System.out.println("This should not happen");
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				final int index=Math.abs(r.nextInt()%this.allocatedResources.size());
				Integer intArr[]=new Integer[1];
				intArr=this.allocatedResources.toArray(intArr);
				final Integer unitId=intArr[index];
				this.allocatedResources.remove(unitId);
				randomClientRequest.clientRequest=new ClientRequestReleaseResource(AndrewsProcess.currentRelativeToTypeAndrewsProcessId(),unitId);
			} catch (IncorrectResourceRequest e) {
				System.out.println("This should not happen");
				e.printStackTrace();
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
			final RandomClientRequest randomClientRequest=generateRandomClientRequest(r);
			final ClientRequest clientRequest=randomClientRequest.clientRequest;
			System.out.println("Client "+AndrewsProcess.currentAndrewsProcessId()+" with relative identity "+AndrewsProcess.currentRelativeToTypeAndrewsProcessId()+" w.r.t. specific type "+this.getClass().getName()+"\n\tSends request "+clientRequest.getClass().getName());
			GlobalProgramState.request.send(clientRequest);
			ServerResponse serverResponse=GlobalProgramState.reply.get(AndrewsProcess.currentRelativeToTypeAndrewsProcessId()).receive();
			if (serverResponse.isSuccess()) {
				switch(randomClientRequest.number) {
				case 0:
					this.allocatedResources.add(((ServerResponseWithUnitId)serverResponse).getUnitId());
					System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" Acquire request resulted in allocation of "+((ServerResponseWithUnitId)serverResponse).getUnitId());
					break;
				case 1: 
					this.allocatedResources.remove(((ClientRequestReleaseResource)clientRequest).getUnitId());
					System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" Release request of "+((ClientRequestReleaseResource)clientRequest).getUnitId()+" completed with success="+((ServerResponse)serverResponse).isSuccess());
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
