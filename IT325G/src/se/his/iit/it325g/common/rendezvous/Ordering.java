package se.his.iit.it325g.common.rendezvous;

import java.util.Comparator;
import java.util.HashSet;

public abstract class Ordering<T extends Entry> extends Observer implements Evaluation {
	private HashSet<T> entrySet=new HashSet<T>();
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
	
	public boolean evaluate() {
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
	void addEntry(T entry) {
		if (!entry.getRendezvous().equals(this.getRendezvous())) {
			throw new IllegalArgumentException("Entry "+entry+" does not belong to Rendezvous "+this.getRendezvous());
		}

		entrySet.add((T) entry);
	}
	
}