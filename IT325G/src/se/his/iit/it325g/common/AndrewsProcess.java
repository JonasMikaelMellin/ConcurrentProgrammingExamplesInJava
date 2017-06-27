package se.his.iit.it325g.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AndrewsProcess extends Thread {
	
	static private int nextIdentity=0;
	static private ConcurrentHashMap<Thread,Integer> t2i=new ConcurrentHashMap<Thread,Integer>();
	static private ConcurrentHashMap<Class<?>,ConcurrentHashMap<Thread,Integer>> c2t2i=new ConcurrentHashMap<Class<?>,ConcurrentHashMap<Thread,Integer>>(); // relative identity with respect to class
	private int andrewsPid;
	private int relativeToTypeAndrewsPid;
	private Class<?> cls;
	
	public int getAndrewsPid() {
		return this.andrewsPid;
	}
	
	private static synchronized int nextAndrewsPid() {
		return nextIdentity++;
	}
	
	
	public static AndrewsProcess[] andrewsProcessFactory(int n, Class<? extends Runnable> runnable) throws InstantiationException, IllegalAccessException {
		
		AndrewsProcess[] result=new AndrewsProcess[n];
		for (int i=0; i<n; ++i) {
			result[i]=new AndrewsProcess(runnable.newInstance());
		}
		return result;
	}
	
	public static class RunnableSpecification {
		private Class<? extends Runnable> runnable;
		private int amount;
		/**
		 * @param runnable
		 * @param amount
		 */
		public RunnableSpecification(Class<? extends Runnable> runnable, int amount) {
			if (amount <1) {
				throw new IllegalArgumentException("amount = "+amount+", must be larger than 1");
			}
			if (runnable==null) {
				throw new IllegalArgumentException("runnable must not be null");				
			}
			this.runnable = runnable;
			this.amount = amount;
		}
		/**
		 * @param runnable
		 */
		public RunnableSpecification(Class<? extends Runnable> runnable) {
			if (runnable==null) {
				throw new IllegalArgumentException("runnable must not be null");				
			}
			this.runnable = runnable;
			this.amount=1;
		}
		/**
		 * @return the runnable
		 */
		public synchronized final Class<? extends Runnable> getRunnable() {
			return runnable;
		}
		/**
		 * @return the amount
		 */
		public synchronized final int getAmount() {
			return amount;
		}
		
		
	}
	public static AndrewsProcess[] andrewsProcessFactory(RunnableSpecification[] runnableSpecification) throws InstantiationException, IllegalAccessException {
		final int n=Arrays.asList(runnableSpecification).stream().mapToInt(rs->rs.getAmount()).sum();
		AndrewsProcess result[]=new AndrewsProcess[n];
		int i=0;
		for (RunnableSpecification rs:runnableSpecification) {
			for (int j=0; j<rs.getAmount(); ++j) {
				final Class<? extends Runnable> cls=rs.getRunnable();
				result[i++]=new AndrewsProcess(cls.newInstance());
			}
		}
		return result;
	}
	private static Runnable newInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void startAndrewsProcesses(AndrewsProcess process[]) {
		for (int i=0; i<process.length; ++i) {
			process[i].start();
		}
	}
	
	private final void initialize() {
		this.andrewsPid=nextAndrewsPid();
		AndrewsProcess.t2i.put(this,this.andrewsPid);
		ConcurrentHashMap<Thread, Integer> t2i=AndrewsProcess.c2t2i.get(this.cls);
		if (t2i==null) {
			t2i=new ConcurrentHashMap<Thread, Integer>();
			c2t2i.put(this.cls, t2i);
		}
		Integer nextRelativeId=t2i.get(this);
		if (nextRelativeId==null) {
			nextRelativeId=0;
			t2i.put(this, 1);
		} else {
			t2i.put(this, nextRelativeId+1);
		}
		this.relativeToTypeAndrewsPid=nextRelativeId;
	}

	public AndrewsProcess() {
		super();
		throw new IllegalStateException("In the IT325G course, only threads based on Runnable are allowed");
	}

	public AndrewsProcess(Runnable target) {
		super(target);
		this.cls=target.getClass();
		this.initialize();

	}

	public AndrewsProcess(String name) {
		super(name);
		throw new IllegalStateException("In the IT325G course, only threads based on Runnable are allowed");

	}

	public AndrewsProcess(ThreadGroup group, Runnable target) {
		super(group, target);
		this.initialize();

	}

	public AndrewsProcess(ThreadGroup group, String name) {
		super(group, name);
		throw new IllegalStateException("In the IT325G course, only threads based on Runnable are allowed");

	}

	public AndrewsProcess(Runnable target, String name) {
		super(target, name);
		this.andrewsPid=nextAndrewsPid();

	}

	public AndrewsProcess(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		this.initialize();

	}

	public AndrewsProcess(ThreadGroup group, Runnable target, String name,
			long stackSize) {
		super(group, target, name, stackSize);
		this.initialize();

	}

	public static int currentAndrewsProcessId() {
		final int result=t2i.get(Thread.currentThread());
		return result;
	}
	
	public static int currentRelativeToTypeAndrewsProcessId() {
		final ConcurrentHashMap<Thread, Integer> t2i=AndrewsProcess.c2t2i.get(((AndrewsProcess)Thread.currentThread()).getClass());
		
		final int result=t2i.get(Thread.currentThread());
		return result;
	}
}
