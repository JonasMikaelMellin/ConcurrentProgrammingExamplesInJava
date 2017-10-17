package se.his.iit.it325g.simpleClientServer;

public abstract class ClientRequestArithmeticOperator extends ClientRequest {

	private int value;

	public ClientRequestArithmeticOperator(int clientId,int value) {
		super(clientId);
		this.value=value;
	}

	@Override
	public final int getRequestValue() {
		return this.value;
	}
	
	public abstract int performOperation(int value);

}
