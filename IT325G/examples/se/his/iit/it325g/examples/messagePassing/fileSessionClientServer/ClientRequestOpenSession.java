package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

public class ClientRequestOpenSession extends ClientRequest {

	public ClientRequestOpenSession(int clientId) throws IncorrectClientRequest {
		super(clientId);
	}



	@Override
	public Operation getOperation() {
		return Operation.OPEN_SESSION;
	}

}
