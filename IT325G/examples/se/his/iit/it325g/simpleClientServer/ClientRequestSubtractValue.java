package se.his.iit.it325g.simpleClientServer;

public class ClientRequestSubtractValue extends ClientRequestArithmeticOperator {

	public ClientRequestSubtractValue(int clientId, int value) {
		super(clientId, value);
	}

	@Override
	public int performOperation(int value) {
		return value-this.getRequestValue();
	}

	@Override
	public Operation getOperation() {
		return Operation.subtract;
	}

}
