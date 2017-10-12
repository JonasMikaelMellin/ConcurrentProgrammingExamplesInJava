package se.his.iit.it325g.common.rendezvous;

public class Result<T> {

	private T object;
	public Result(T o) {
		this.object=o;
	}
	
	public final boolean isOfClass(Class<?> cls) {
		return cls.isInstance(this.object);
	}
	
	public T getObject() {
		return this.object;
	}

}
