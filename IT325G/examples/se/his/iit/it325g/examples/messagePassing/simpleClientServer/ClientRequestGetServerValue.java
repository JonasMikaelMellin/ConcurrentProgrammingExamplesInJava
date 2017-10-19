package se.his.iit.it325g.examples.messagePassing.simpleClientServer;

public class ClientRequestGetServerValue extends ClientRequest {

	public ClientRequestGetServerValue(int clientId) {
		super(clientId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRequestValue() {
		return 0;
	}

	@Override
	public Operation getOperation() {
		return Operation.getServerValue;
	}

}
