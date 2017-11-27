package se.his.iit.it325g.common.rendezvous;

public abstract class RendezvousCallImplementation implements Runnable {
	protected Rendezvous rendezvous;
	public RendezvousCallImplementation() {
	}
	
	abstract Result<?> call(Entry entry, Object[] parameter);

	/**
	 * @return the rendezvous
	 */
	synchronized final Rendezvous getRendezvous() {
		return rendezvous;
	}

	/**
	 * @param rendezvous the rendezvous to set
	 */
	synchronized  void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}
	
	

}
