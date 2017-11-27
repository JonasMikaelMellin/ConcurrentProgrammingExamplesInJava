package se.his.iit.it325g.common.rendezvous;

import java.util.Comparator;
import java.util.HashSet;

public  class Ordering<T extends QueuedAndrewsProcess> extends Observer implements Evaluation {
	private HashSet<Entry> entrySet=new HashSet<Entry>();
	private Rendezvous.Order order;
	private Comparator<T> comparator=null;
	public Ordering(String name,Rendezvous rendezvous) {
		super(name,rendezvous);
		this.order=Rendezvous.Order.FIRST_COME_FIRST_SERVED;
	}
	public Ordering(String name,Rendezvous rendezvous,Rendezvous.Order order,Comparator<T> comparator) {
		super(name,rendezvous);
		this.order=order;
		this.comparator=comparator;
	}
	
	public boolean evaluate(Object[] parameter) {
		return false;
	}
	/**
	 * @return the order
	 */
	public synchronized final Rendezvous.Order getOrder() {
		return order;
	}
	/**
	 * @return the comparator
	 */
	public synchronized final Comparator<?> getComparator() {
		return comparator;
	}
	@SuppressWarnings("unchecked")
	void addEntry(Entry qap) {
		if (!qap.getRendezvous().equals(this.getRendezvous())) {
			throw new IllegalArgumentException("Entry "+qap+" does not belong to Rendezvous "+this.getRendezvous());
		}

		entrySet.add( qap);
	}
	
}