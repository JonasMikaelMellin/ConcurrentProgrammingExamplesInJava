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
			case ADD: case SUBTRACT:
				try {
					this.serverValue=((ClientRequestArithmeticOperator)clientRequest).performOperation(this.serverValue);
					serverResponse=new ServerResponse(true);
				}
				catch (Exception e) {
					serverResponse=new ServerResponse(e);
				}
				break;
			case GET_SERVER_VALUE:
				try {
					serverResponse=new ServerResponseWithValue(this.serverValue);
				}
				catch (Exception e) {
					serverResponse=new ServerResponse(e);
				}
				break;
			case CLOSE_SIMULATED_CLIENT_SESSION:
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
