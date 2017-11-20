package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

public class ServerResponseInteger extends ServerResponse {
	private int value;

	public ServerResponseInteger(int value) {
		super(true);
		this.value=value;
	}

	public ServerResponseInteger(Exception exception) {
		super(exception);
	}
	public int  getValue() {
		return this.value;
	}

}
