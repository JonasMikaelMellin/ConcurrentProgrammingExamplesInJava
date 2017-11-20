package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

public class ClientRequestIndex extends ClientRequestNotOpenSession {
	private int index;

	public ClientRequestIndex(int clientId,int index) throws IncorrectClientRequest {
		super(clientId);
		if (index<0) {
			throw new IncorrectClientRequest("Index < 0");
		}
		this.index=index;
	}

	@Override
	public Operation getOperation() {
		throw new IllegalStateException("Cannot access operation of ClientRequestIndex");
	}

	/**
	 * @return the index
	 */
	public synchronized final int getIndex() {
		return index;
	}
	

}
