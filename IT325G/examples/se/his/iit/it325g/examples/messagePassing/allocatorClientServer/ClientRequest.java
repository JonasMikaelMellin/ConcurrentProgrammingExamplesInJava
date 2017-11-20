package se.his.iit.it325g.examples.messagePassing.allocatorClientServer;

public abstract class ClientRequest {
	private int clientId;
	
	enum Operation {ACQUIRE_RESOURCE,RELEASE_RESOURCE,CLOSE_SESSION};

	public ClientRequest(int clientId) {
		this.clientId=clientId;

	}
	



	/**
	 * @return the clientId
	 */
	public synchronized final int getClientId() {
		return clientId;
	}
	
	public abstract Operation getOperation();
	

}
