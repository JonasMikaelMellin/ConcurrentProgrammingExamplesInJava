package se.his.iit.it325g.examples.distributedAlgorithm.heartbeat.topologyExample;

public class HeartbeatMessage {
	private boolean[][] fragment;
	public HeartbeatMessage(boolean[][] fragment) {
		this.fragment=new boolean[fragment.length][fragment[0].length];
		for (int i=0; i<fragment.length; ++i) {
			for (int j=0; j<fragment[0].length; ++j) {
				this.fragment[i][j]=fragment[i][j];
			}
		}
	}
	public boolean get(int i,int j) {
		return this.fragment[i][j];
	}

}
