package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

public abstract class ClientRequest {
	private int clientId;
	
	enum Operation {READ,WRITE,OPEN_SESSION, CLOSE_SESSION};

	public ClientRequest(int clientId) throws IncorrectClientRequest {
		if (clientId<0) {
			throw new IncorrectClientRequest("Response channel <0");
		}
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
