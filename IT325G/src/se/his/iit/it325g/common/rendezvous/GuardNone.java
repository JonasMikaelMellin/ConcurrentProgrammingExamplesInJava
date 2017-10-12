package se.his.iit.it325g.common.rendezvous;

public class GuardNone extends Guard {

	public static String noGuardString="No guard";
	public GuardNone(Rendezvous rendezvous) {
		super(noGuardString, rendezvous);
	}
	/* (non-Javadoc)
	 * @see se.his.iit.it325g.common.rendezvous.Guard#evaluate()
	 */
	@Override
	public boolean evaluate() {
		return true;
	}

	
}
