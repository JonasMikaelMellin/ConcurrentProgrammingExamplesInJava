package se.his.iit.it325g.allocatorClientServer;

public class ClientRequestReleaseResource extends ClientRequest {

	private int unitId;
	public ClientRequestReleaseResource(int clientId, int unitId) throws IncorrectResourceRequest {
		super(clientId);
		this.unitId=unitId;
	}



	@Override
	public Operation getOperation() {
		return Operation.RELEASE_RESOURCE;
	}
	
	public int getUnitId() {
		return this.unitId;
	}

}
