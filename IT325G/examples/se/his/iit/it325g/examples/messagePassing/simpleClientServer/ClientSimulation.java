//    IT325G - Concurrent programming examples in Java
//    Copyright (C) 2017  Jonas Mikael Mellin
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.


package se.his.iit.it325g.examples.messagePassing.simpleClientServer;

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
					System.out.println("\tClient "+AndrewsProcess.currentAndrewsProcessId()+" Request completed with success="+serverResponse.isSuccess());
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
