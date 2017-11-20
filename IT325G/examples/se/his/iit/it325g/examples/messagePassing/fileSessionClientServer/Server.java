package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.examples.messagePassing.fileSessionClientServer.ClientRequest.Operation;

public class Server implements Runnable {
	

	private HashMap<Integer,Integer> i2i=new HashMap<Integer,Integer>();
	public Server() {
	}

	@Override
	public void run() {
		while (true) {
			final ClientRequest clientRequest=GlobalProgramState.openSession.receive();
			if (clientRequest.getOperation()==Operation.OPEN_SESSION) {
				ServerResponse openResponse=new ServerResponse(true);
				GlobalProgramState.reply.get(clientRequest.getClientId()).send(openResponse);
				ServerResponse serverResponse=null;
				boolean sessionIsOpen=true;
				while (sessionIsOpen) {
					final ClientRequest operationRequest=GlobalProgramState.request.receive();

					switch(operationRequest.getOperation()) {
					case READ: 
						try {
							Integer result=i2i.get(((ClientRequestIndex)operationRequest).getIndex());
							if (result==null) {
								serverResponse=new ServerResponseInteger(0);
							} else {
								serverResponse=new ServerResponseInteger(result);
							}
						}
						catch (Exception e) {
							serverResponse=new ServerResponse(e);
						}
						break;
					case WRITE:
						try {
							final Integer index=((ClientRequestWrite)operationRequest).getIndex();
							final Integer value=((ClientRequestWrite)operationRequest).getValue();
							i2i.put(index,value);
							serverResponse=new ServerResponse(true);
						}
						catch (Exception e) {
							serverResponse=new ServerResponse(e);
						}
						break;
					case OPEN_SESSION:
						serverResponse=new ServerResponse(new IllegalArgumentException("Cannot open session in an open session"));
						break;
					case CLOSE_SESSION:
						serverResponse=new ServerResponse(true);
						sessionIsOpen=false;
						break;
					default:
						break;

					}
					// send response back on client's reply channel, note that
					// client id is relative to the type not relative to all processes
					if (serverResponse!=null) {
						GlobalProgramState.reply.get(operationRequest.getClientId()).send(serverResponse);
					}

				}
			} else {
				GlobalProgramState.reply.get(clientRequest.getClientId()).send(new ServerResponse(new IllegalArgumentException("Incorrect request")));
				// error 
				
			}
		} 
	}

}
