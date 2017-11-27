package se.his.iit.it325g.common.rendezvous;


public abstract class Entry extends Observer implements Evaluation {
	private Guard guard;
	private Action action;
	private Ordering ordering;
	/**
	 * @param guard
	 * @param action
	 */
	public Entry(String name,Rendezvous rendezvous, Guard guard, Action action,Ordering ordering) {
		super(name,rendezvous);
		if (action==null) {
			throw new IllegalArgumentException("Action cannot be null");
		}
		if (guard==null) {
			this.guard=new GuardNone(rendezvous);
		} else {
			this.guard = guard;
		}
		this.action = action;
		if (ordering==null) {
			this.ordering=new OrderingDefault(rendezvous);
		} else {
			this.ordering = ordering;
		}
		this.guard.addEntry(this);
		this.action.addEntry(this);
		this.ordering.addEntry(this);
		rendezvous.addEntry(this);
	}
	public boolean evaluate(Object[] parameter) {
		return false;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"Entry [guard=%s, action=%s, ordering=%s, getName()=%s]",
				guard, action, ordering, getName());
	}
	/**
	 * @return the guard
	 */
	public synchronized final Guard getGuard() {
		return guard;
	}
	/**
	 * @return the action
	 */
	public synchronized final Action getAction() {
		return action;
	}
	/**
	 * @return the ordering
	 */
	public synchronized final Ordering getOrdering() {
		return ordering;
	}
	
	public abstract Result<?> call(Object... object);
	
	
	
	
	
}