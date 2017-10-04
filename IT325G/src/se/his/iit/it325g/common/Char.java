package se.his.iit.it325g.common;

public class Char implements Comparable<Char> {
	
	private char value;


	public Char(char ch) {
		this.value=ch;
	}
	@Override
	public int compareTo(Char o) {
		return this.value-o.value;
	}
	public char getValue() {
		return this.value;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s", value);
	}
	
	
	

}
