package se.his.iit.it325g.common.rendezvous;

import java.util.HashSet;

public abstract class Action extends Observer implements Evaluation {
	private HashSet<Entry> entrySet=new HashSet<Entry>();
	private Result<?> result;
	public Action(String name,Rendezvous rendezvous) {
		super(name,rendezvous);
	}
	public boolean evaluate(Object[] parameter) {
		return false;
	}
	void addEntry(Entry entry) {
		if (!entry.getRendezvous().equals(this.getRendezvous())) {
			throw new IllegalArgumentException("Entry "+entry+" does not belong to Rendezvous "+this.getRendezvous());
		}
		entrySet.add(entry);
	}
	public Result<?> getResult() {
		return this.result;
	}
	protected void setResult(Result<?> result) {
		this.result=result;
	}
	
}