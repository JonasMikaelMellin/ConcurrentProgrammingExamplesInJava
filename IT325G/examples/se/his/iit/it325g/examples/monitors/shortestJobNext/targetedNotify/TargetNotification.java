package se.his.iit.it325g.examples.monitors.shortestJobNext.targetedNotify;

import java.util.HashMap;

import se.his.iit.it325g.common.AndrewsProcess;

public class TargetNotification {
	private HashMap<Integer,Object> i2o=new HashMap<Integer,Object>();
	
	public   void waitOnId(int id) {
		Object o;
		synchronized(this.i2o) {
			o=this.i2o.get(id);
			if (o==null) {
				o=new Object();
				this.i2o.put(id,o);
			}
		}
		synchronized(o) {
			try {
				o.wait();
			} catch (InterruptedException e) {
				AndrewsProcess.defaultInterruptedExceptionHandling(e);
			}
		}
	}
	public   void notifyId(int id) {
		Object o;
		synchronized(this.i2o) {
			o=this.i2o.get(id);
			if (o==null) {
				o=new Object();
				this.i2o.put(id,o);
			}
		}
		synchronized(o) {
			o.notify();
		}
	}
	

}
