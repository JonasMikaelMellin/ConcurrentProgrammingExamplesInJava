package se.his.iit.it325g.examples.messagePassing.allocatorClientServer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import se.his.iit.it325g.common.AndrewsProcess;

public class Server implements Runnable {
	
	private int serverValue=0;
	private int numberOfSessions=GlobalProgramState.numberOfClients; // simplified & static setup
	private HashSet<Integer> unitSet=new HashSet<Integer>();
	private Queue<ClientRequest> queue=new LinkedList<ClientRequest>();

	public Server() {
	}

	@Override
	public void run() {
		this.unitSet.addAll(IntStream.range(0, GlobalProgramState.numberOfResources).boxed().collect(Collectors.toList()));
		while (true) {
			final ClientRequest clientRequest=GlobalProgramState.request.receive();
			ServerResponse serverResponse=null;
			switch(clientRequest.getOperation()) {
			case ACQUIRE_RESOURCE: 
				try {
					if (this.unitSet.size()>0) {
						final Integer unitId=unitSet.iterator().next();
						this.unitSet.remove(unitId);
						serverResponse=new ServerResponseWithUnitId(unitId);						
					} else {
						queue.add(clientRequest);
					}
				}
				catch (Exception e) {
					serverResponse=new ServerResponse(e);
				}
				break;
			case RELEASE_RESOURCE:
				try {
					if (queue.isEmpty()) {
						this.unitSet.add(((ClientRequestReleaseResource)clientRequest).getUnitId());
					} else {
						ClientRequest queuedRequest=queue.poll();
						GlobalProgramState.reply.get(queuedRequest.getClientId()).send(new ServerResponseWithUnitId(((ClientRequestReleaseResource)clientRequest).getUnitId()));
					}
					serverResponse=new ServerResponse(true);
				}
				catch (Exception e) {
					serverResponse=new ServerResponse(e);
				}
				break;
			case CLOSE_SESSION:
				--this.numberOfSessions;
				serverResponse=new ServerResponse(true);
				break;
			default:
				break;
			
			}
			// send response back on client's reply channel, note that
			// client id is relative to the type not relative to all processes
			if (serverResponse!=null) {
				GlobalProgramState.reply.get(clientRequest.getClientId()).send(serverResponse);
			}
			if (this.numberOfSessions<=0) {
				AndrewsProcess.uninterruptibleMinimumDelay(100);

				System.out.println("Final session closed, terminating");
				System.exit(0);
			}
		}
	}

}
