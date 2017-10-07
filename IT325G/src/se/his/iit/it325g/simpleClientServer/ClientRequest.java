package se.his.iit.it325g.simpleClientServer;

public abstract class ClientRequest {
	private int clientId;
	
	enum Operation {getServerValue,add,subtract,closeSession};

	public ClientRequest(int clientId) {
		this.clientId=clientId;

	}
	

	public abstract int getRequestValue();


	/**
	 * @return the clientId
	 */
	public synchronized final int getClientId() {
		return clientId;
	}
	
	public abstract Operation getOperation();
	

}
