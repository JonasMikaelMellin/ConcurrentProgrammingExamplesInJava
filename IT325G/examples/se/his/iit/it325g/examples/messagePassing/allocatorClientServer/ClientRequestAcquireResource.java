package se.his.iit.it325g.examples.messagePassing.allocatorClientServer;

public class ClientRequestAcquireResource extends ClientRequest {

	public ClientRequestAcquireResource(int clientId) throws IncorrectResourceRequest  {
		super(clientId);

	}



	@Override
	public Operation getOperation() {
		return Operation.ACQUIRE_RESOURCE;
	}

}
