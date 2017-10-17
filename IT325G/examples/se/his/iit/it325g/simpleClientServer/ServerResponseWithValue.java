package se.his.iit.it325g.simpleClientServer;

public class ServerResponseWithValue extends ServerResponse {
	private int value;

	public ServerResponseWithValue(int value) {
		super(true);
		this.value=value;
	}

	public ServerResponseWithValue(Exception exception) {
		super(exception);
	}
	public int  getValue() {
		return this.value;
	}

}
