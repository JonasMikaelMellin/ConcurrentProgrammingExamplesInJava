package se.his.iit.it325g.examples.messagePassing.simpleClientServer;

public class ClientRequestAddValue extends ClientRequestArithmeticOperator {

	public ClientRequestAddValue(int clientId, int value) {
		super(clientId, value);
	}

	@Override
	public int performOperation(int value) {
		return value+this.getRequestValue();
	}

	@Override
	public Operation getOperation() {
		return Operation.add;
	}

}
