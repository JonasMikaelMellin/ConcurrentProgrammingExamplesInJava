package se.his.iit.it325g.common.rendezvous;

public class Observer {
	private String name;
	private Rendezvous rendezvous;
	public Observer(String name,Rendezvous rendezvous) {
		if (rendezvous==null) {
			throw new IllegalArgumentException("Rendezvous cannot be null");
		}
		if (name==null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		this.name=name;
		this.rendezvous=rendezvous;
	}
	
	/**
	 * @return the rendezvous
	 */
	public synchronized final Rendezvous getRendezvous() {
		return rendezvous;
	}

	/**
	 * @return the name
	 */
	public synchronized final String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Observer [name=%s, rendezvous=%s]", name,
				rendezvous);
	}
	
	
	
}