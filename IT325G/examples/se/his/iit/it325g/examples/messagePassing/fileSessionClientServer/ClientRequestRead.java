package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

public class ClientRequestRead extends ClientRequestIndex {

	public ClientRequestRead(int clientId,int index) throws IncorrectClientRequest  {
		super(clientId,index);

	}



	@Override
	public Operation getOperation() {
		return Operation.READ;
	}

}
