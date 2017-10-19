package se.his.iit.it325g.examples.messagePassing.allocatorClientServer;

public class ClientRequestCloseSession extends ClientRequest {

	public ClientRequestCloseSession(int clientId) {
		super(clientId);
	}



	@Override
	public Operation getOperation() {
		return Operation.CLOSE_SESSION;
	}

}
