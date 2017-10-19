package se.his.iit.it325g.examples.messagePassing.allocatorClientServer;

public class ServerResponse {
	private boolean success=true;
	private Exception exception;

	/**
	 * @param success
	 */
	public ServerResponse(boolean success) {
		this.success = success;
	}
	

	/**
	 * @param exception
	 */
	public ServerResponse(Exception exception) {
		this.success=false;
		this.exception = exception;
	}


	/**
	 * @return the success
	 */
	public synchronized final boolean isSuccess() {
		return success;
	}


	/**
	 * @return the exception
	 */
	public synchronized final Exception getException() {
		return exception;
	}
	
	
	
	

}
