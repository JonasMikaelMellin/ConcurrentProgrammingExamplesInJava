package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

public class ClientRequestCloseSession extends ClientRequestNotOpenSession {

	public ClientRequestCloseSession(int clientId) throws IncorrectClientRequest {
		super(clientId);
	}



	@Override
	public Operation getOperation() {
		return Operation.CLOSE_SESSION;
	}

}
