package se.his.iit.it325g.common.rendezvous;

import java.util.HashSet;

public abstract class Guard extends Observer implements Evaluation {
	private HashSet<Entry> entrySet=new HashSet<Entry>();
	public Guard(String name,Rendezvous rendezvous) {
		super(name,rendezvous);
	}
	void addEntry(Entry entry) {
		if (!entry.getRendezvous().equals(this.getRendezvous())) {
			throw new IllegalArgumentException("Entry "+entry+" does not belong to Rendezvous "+this.getRendezvous());
		}
		this.entrySet.add(entry);
	}
	public boolean evaluate(Object[] parameter) {
		return false;
	}		

}