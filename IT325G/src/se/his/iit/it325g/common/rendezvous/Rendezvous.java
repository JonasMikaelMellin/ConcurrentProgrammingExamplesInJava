package se.his.iit.it325g.common.rendezvous;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;

import se.his.iit.it325g.common.AndrewsProcess;

public abstract class Rendezvous implements Runnable {

	protected String name;
	private HashMap<String,Entry> entryMap = new HashMap<String,Entry>();
	HashMap<String,Queue<QueuedAndrewsProcess>> s2eq = new HashMap<String,Queue<QueuedAndrewsProcess>>();
	private RendezvousCallImplementation rendezvousCallImplementation;

	/**
		 * Enumeration, either FCFS or Ordered.
		 * 
		 * @author melj
		 * 
		 */
		protected enum Order {
			FIRST_COME_FIRST_SERVED, 
			ORDERED 
			
			}

	protected Rendezvous(String name, RendezvousCallImplementation rendezvousCallImplementation) {
		if (name==null) {
			throw new IllegalArgumentException("Rendezvous name cannot be null");
		}
		this.name=name;
		this.rendezvousCallImplementation=rendezvousCallImplementation;
	}

	/**
	 * @return the name
	 */
	public final synchronized String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("Rendezvous [name=%s]", name);
	}

	public void run() {
		rendezvousCallImplementation.run();
	}

	public void initialize() {
		this.rendezvousCallImplementation.setRendezvous(this);
	}

	protected final void addEntry(final Entry entry) {
		this.entryMap.put(entry.getName(),entry);
		
	}

	public final Entry getEntry(final String name) {
		if (!this.entryMap.containsKey(name)) {
			throw new IllegalArgumentException("Entry named "+name+" is not found");
		}
		return this.entryMap.get(name);
	}
	public static Rendezvous createRendezevous(Class<?> cls, String name, RendezvousCallImplementation rendezvousCallImplementation) {
		Constructor<?> constructor;
		Rendezvous rendezvous=null;
		try {
			constructor = cls.getConstructor(String.class,RendezvousCallImplementation.class);
			rendezvous=(Rendezvous) constructor.newInstance(name,rendezvousCallImplementation);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalStateException(e);
		}
		rendezvousCallImplementation.setRendezvous(rendezvous);
		return rendezvous;
	}
	protected final Result<?> call(Entry entry, Object[] parameter) {
		return this.rendezvousCallImplementation.call(entry, parameter);
	}

	public Set<Entry> getEntries() {
		return (Set<Entry>) entryMap.values();
	}


}