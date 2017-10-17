package se.his.iit.it325g.peerValueExchange.ring;

public class SmallestAndLargestValue {
	public static class Sentinel extends SmallestAndLargestValue {

		public Sentinel() {
			super();
		}
		/**
		 * @return the smallest
		 */
		@Override
		public synchronized final int getSmallest() {
			throw new IllegalStateException("Cannot request smallest value from the sentinel");
		}

		/**
		 * @return the largest
		 */
		@Override
		public synchronized final int getLargest() {
			throw new IllegalStateException("Cannot request largest value from the sentinel");
		}

		/**
		 * @return the sentinel
		 */
		@Override
		public synchronized  boolean isSentinel() {
			return false;
		}
	}



	private int smallest;
	private int largest;
	
	private SmallestAndLargestValue() {
	}

	public SmallestAndLargestValue(int n1,int n2) {
		if (n1>n2) {
			this.smallest=n2;
			this.largest=n1;
		} else {
			this.smallest=n1;
			this.largest=n2;
		}
	}
	


	/**
	 * @return the smallest
	 */
	public synchronized  int getSmallest() {
		return smallest;
	}

	/**
	 * @return the largest
	 */
	public synchronized  int getLargest() {
		return largest;
	}



	/**
	 * @return the sentinel
	 */
	public synchronized  boolean isSentinel() {
		return false;
	}
	
	
	
	

}
