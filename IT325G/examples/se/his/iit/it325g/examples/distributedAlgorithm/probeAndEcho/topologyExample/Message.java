package se.his.iit.it325g.examples.distributedAlgorithm.probeAndEcho.topologyExample;

public abstract class Message {
	
	enum Type { PROBE, ECHO}

	private int from;
	
	public Message(int from) {
		this.from = from;
	}

	public Type getType() {
		return null;
	}

	public final int getFrom() {
		return this.from;
	}

}
