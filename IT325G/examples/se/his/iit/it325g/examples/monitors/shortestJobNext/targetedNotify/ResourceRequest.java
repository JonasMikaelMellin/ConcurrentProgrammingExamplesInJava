package se.his.iit.it325g.examples.monitors.shortestJobNext.targetedNotify;

public class ResourceRequest implements Comparable<ResourceRequest> {
	private int id;
	private int rank;
	
	

	/**
	 * @param rank
	 * @param id
	 */
	public ResourceRequest(int id, int rank) {
		this.rank = rank;
		this.id = id;
	}



	@Override
	public int compareTo(ResourceRequest arg0) {
		return this.rank-arg0.rank;
	}



	/**
	 * @return the id
	 */
	public synchronized final int getId() {
		return id;
	}



	/**
	 * @return the rank
	 */
	public synchronized final int getRank() {
		return rank;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("PriorityQueueElement [rank=%s, id=%s]", rank, id);
	}

}
