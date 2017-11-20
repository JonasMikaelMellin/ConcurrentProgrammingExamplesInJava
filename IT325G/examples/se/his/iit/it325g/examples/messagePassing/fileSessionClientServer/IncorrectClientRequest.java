package se.his.iit.it325g.examples.messagePassing.fileSessionClientServer;

public class IncorrectClientRequest extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2751355590519896329L;

	public IncorrectClientRequest() {
		super();
	}

	public IncorrectClientRequest(String arg0) {
		super(arg0);
	}

	public IncorrectClientRequest(Throwable arg0) {
		super(arg0);
	}

	public IncorrectClientRequest(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IncorrectClientRequest(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
