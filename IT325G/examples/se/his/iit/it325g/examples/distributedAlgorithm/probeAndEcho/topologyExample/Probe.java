package se.his.iit.it325g.examples.distributedAlgorithm.probeAndEcho.topologyExample;

import java.util.Collection;
import java.util.Vector;

public class Probe extends Message {
	private Vector<Integer> pedigree = new Vector<Integer>();
	private Vector<Probe> previousProbeVector = new Vector<Probe>();
	private int originator;
	public Probe(Vector<Integer> previousPedigree,int id, Probe previousProbe) {
		super(id);
		if (previousPedigree!=null) {
			this.pedigree.addAll(previousPedigree);
		} else {
			this.originator=id;
		}
		this.pedigree.add(id);
		if (previousProbe!=null) {
			this.previousProbeVector.add(previousProbe);
		}
	}
	
	public boolean isInPedigree(int id) {
		return this.pedigree.contains(id);
	}
	public final int getOriginator() {
		return this.originator;
	}

	/* (non-Javadoc)
	 * @see se.his.iit.it325g.examples.distributedAlgorithm.probeAndEchoExample.Message#getType()
	 */
	@Override
	public Type getType() {
		return Type.PROBE;
	}

	public Vector<Integer> getPedigree() {
		return this.pedigree;
	}
	
	public Vector<Probe> getPreviousProbes() {
		return this.previousProbeVector;
	}
	
	public Probe getPreviousProbe(int id) {
		return this.previousProbeVector.get(this.pedigree.indexOf(id));
	}
	
	
	
}
