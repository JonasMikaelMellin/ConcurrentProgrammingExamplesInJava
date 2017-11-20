package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

public class ClientRequestWrite extends ClientRequestIndex {

	private int value;
	public ClientRequestWrite(int clientId, int index, int value) throws IncorrectClientRequest  {
		super(clientId,index);
		this.value=value;
	}



	@Override
	public Operation getOperation() {
		return Operation.WRITE;
	}
	
	public int getValue() {
		return this.value;
	}

}
