package se.his.iit.it325g.allocatorClientServer;

public class ServerResponseWithUnitId extends ServerResponse {
	private int value;

	public ServerResponseWithUnitId(int value) {
		super(true);
		this.value=value;
	}

	public ServerResponseWithUnitId(Exception exception) {
		super(exception);
	}
	public int  getUnitId() {
		return this.value;
	}

}
