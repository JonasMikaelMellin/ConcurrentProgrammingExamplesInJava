package se.his.iit.it325g.examples.messagePassing.simpleClientServer;

public class ClientRequestCloseSession extends ClientRequest {

	public ClientRequestCloseSession(int clientId) {
		super(clientId);
	}

	@Override
	public int getRequestValue() {
		return 0;
	}

	@Override
	public Operation getOperation() {
		return Operation.closeSession;
	}

}
