package se.his.iit.it325g.common.rendezvous;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import se.his.iit.it325g.common.AndrewsProcess;

public abstract class Rendezvous implements Runnable {
	private String name;
	private HashMap<String,Entry> entryMap=new HashMap<String,Entry>();
	private  HashMap<String,Queue<AndrewsProcess>> s2eq=new HashMap<String,Queue<AndrewsProcess>>();
	
	
	/**
	 * Enumeration, either FCFS or Ordered.
	 * 
	 * @author melj
	 * 
	 */
	enum Order {
		FIRST_COME_FIRST_SERVED, 
		ORDERED 
		
		};

	public Rendezvous(String name) {
		if (name==null) {
			throw new IllegalArgumentException("Rendezvous name cannot be null");
		}
		this.name=name;
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
		return String.format("Rendezvous [name=%s]", name);
	}
	
	protected final synchronized Result<?> call(Entry entry) {
		if (!entry.getRendezvous().equals(this)) {
			throw new IllegalArgumentException("Entry "+entry+" does not belong to rendezvous "+this);
		}
		Result<?> result=null;
		Queue<AndrewsProcess> eq=null;
		if (!entry.getGuard().evaluate()) {
			eq=s2eq.get(entry.getName());
			if (eq==null) {
				switch (entry.getOrdering().getOrder()) {
				case FIRST_COME_FIRST_SERVED:
					eq=new LinkedList<AndrewsProcess>();
					break;
				case ORDERED:
					eq=new PriorityQueue<AndrewsProcess>(entry.getOrdering().getComparator());
					break;
				default:
					throw new IllegalArgumentException("The alternatives "+entry.getOrdering().getOrder()+" is not implemented");				
				}

				s2eq.put(entry.getName(), eq);
			}
			eq.add(AndrewsProcess.currentAndrewsProcess());
			boolean guardFlag=entry.getGuard().evaluate();
			while (!guardFlag || (guardFlag && !AndrewsProcess.currentAndrewsProcess().equals(eq.peek()))) {
				try {
					this.wait();
				} catch (InterruptedException interruptedException) {
					AndrewsProcess.defaultInterruptedExceptionHandling(interruptedException);

				}
				guardFlag=entry.getGuard().evaluate();
			}
		}
		if (eq!=null && AndrewsProcess.currentAndrewsProcess().equals(eq.peek())) {
			eq.poll();
		}
		entry.getAction().evaluate();
		this.notifyAll();
		result=entry.getAction().getResult();
		return result;
	}


	@Override
	public void run() {
	}


	public abstract void initialize();


	final void addEntry(final Entry entry) {
		this.entryMap.put(entry.getName(),entry);
		
	}
	public final Entry getEntry(final String name) {
		if (!this.entryMap.containsKey(name)) {
			throw new IllegalArgumentException("Entry named"+name+" is not found");
		}
		return this.entryMap.get(name);
	}
		
	
}
