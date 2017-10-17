package se.his.iit.it325g.common;

public abstract class Chan<T> {

	public Chan() {
		super();
	}

	public abstract T receive();

	public abstract void send(T value);

}