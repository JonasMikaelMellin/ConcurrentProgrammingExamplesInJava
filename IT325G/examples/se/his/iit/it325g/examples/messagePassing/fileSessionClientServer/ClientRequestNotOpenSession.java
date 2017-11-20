package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

public class ClientRequestNotOpenSession extends ClientRequest {

	public ClientRequestNotOpenSession(int clientId)
			throws IncorrectClientRequest {
		super(clientId);
	}



	@Override
	public Operation getOperation() {
		throw new IllegalStateException("Should not be queries");
	}

}
