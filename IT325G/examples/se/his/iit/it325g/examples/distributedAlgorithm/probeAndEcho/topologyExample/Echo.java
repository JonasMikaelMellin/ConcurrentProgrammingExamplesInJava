package se.his.iit.it325g.examples.distributedAlgorithm.probeAndEcho.topologyExample;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.examples.distributedAlgorithm.probeAndEcho.topologyExample.Message.Type;

public class Echo extends Message {
	private boolean[][] fragment;
	private Message probe;
	public Echo(boolean[][] fragment, Message probe) {
		super(AndrewsProcess.currentRelativeToTypeAndrewsProcessId());
		this.probe = probe;
		if (fragment==null) {
			this.fragment=null;
			return;
		}
		this.fragment=new boolean[fragment.length][fragment[0].length];
		for (int i=0; i<fragment.length; ++i) {
			for (int j=0; j<fragment[0].length; ++j) {
				this.fragment[i][j]=fragment[i][j];
			}
		}
	}
	public boolean get(int i,int j) {
		if (this.fragment==null) {
			throw new IllegalStateException("Cannot query a null state for the value in a matrix");
		}
		return this.fragment[i][j];
	}
	public boolean isEmpty() {
		return this.fragment==null;
	}
	/* (non-Javadoc)
	 * @see se.his.iit.it325g.examples.distributedAlgorithm.probeAndEchoExample.Message#getType()
	 */
	@Override
	public Type getType() {
		return Type.ECHO;
	}
	public final Message getProbe() {
		return this.probe;
	}

}
