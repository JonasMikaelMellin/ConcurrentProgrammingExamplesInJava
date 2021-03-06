//    IT325G - Concurrent programming examples in Java
//    Copyright (C) 2017  Jonas Mikael Mellin
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package se.his.iit.it325g.common;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import se.his.iit.it325g.common.rendezvous.Rendezvous;

/**
 * This class is a wrapper of the Thread class. The purpose is to make threads in Java conform to the process concept used
 * in the course literature as much as possible as well as force students to employ the Runnable interface rather than extending
 * the Thread class. The reason for the former is purely pedagogical. The reason of the latter is that employing the Runnable 
 * interface does not restrict classes from, for example, being used in the java.lang.concurrent package. 
 * @author Jonas Mellin
 * @see Thread, Runnable
 */
public class AndrewsProcess extends Thread {

	static volatile private int nextIdentity = 0;
	static private ConcurrentHashMap<Thread, Integer> t2i = new ConcurrentHashMap<Thread, Integer>();
	static private ConcurrentHashMap<Class<?>, ConcurrentHashMap<Thread, Integer>> c2t2i = new ConcurrentHashMap<Class<?>, ConcurrentHashMap<Thread, Integer>>(); // relative
																																									// identity
																																									// with
																																									// respect
																																									// to
																																									// class
	private int andrewsPid; // the global process identity within the program
	private int relativeToTypeAndrewsPid; // process identity relative to type, to create arrays of a particular type
	private Class<?> cls; // the type of the AndrewsProcess
	private Runnable runnable; // the Runnable instance for this particular process

	/**
	 * 
	 * @return the current Andrews process identity (an integer >= 0)
	 */

	public int getAndrewsPid() {
		return this.andrewsPid;
	}

	/**
	 * 
	 * @return the next Andrews process identity
	 */

	private static synchronized int nextAndrewsPid() {
		return nextIdentity++;
	}

	/**
	 * An Andrews process factory method that creates <code>n</code> Andrews
	 * processes (Java threads) based on the Runnable class
	 * <code>runnable</code>
	 * 
	 * @param runnable
	 *            the runnable class specification
	 * @param n
	 *            number of Andrews processes to start
	 * @return an array of Andrews process identities
	 * @throws InstantiationException
	 *             @see Thread(Runnable) constructor
	 * @throws IllegalAccessException
	 *             @see Thread(Runnable) constructor
	 */

	public static AndrewsProcess[] andrewsProcessFactory(
			Class<? extends Runnable> runnable, int n)
			throws InstantiationException, IllegalAccessException {

		AndrewsProcess[] result = new AndrewsProcess[n];
		for (int i = 0; i < n; ++i) {
			result[i] = new AndrewsProcess(runnable.newInstance());
		}
		return result;
	}

	/**
	 * This class provides a uniform of specifying a set of process
	 * specification consisting of pairs of Runnable and initial number of
	 * processes.
	 * 
	 * @author melj
	 *
	 */
	public static class RunnableSpecification {
		private Class<? extends Runnable> runnableCls;
		private int amount;

		/**
		 * Creates a RunnableSpecification object.
		 * 
		 * @param runnableCls
		 *            the runnable class
		 * @param amount
		 *            the amount of initial processes
		 */
		public RunnableSpecification(Class<? extends Runnable> runnableCls,
				int amount) {
			if (amount < 1) {
				throw new IllegalArgumentException("amount = " + amount
						+ ", must be larger than 1");
			}
			if (runnableCls == null) {
				throw new IllegalArgumentException("runnable must not be null");
			}
			this.runnableCls = runnableCls;
			this.amount = amount;
		}

		/**
		 * Creates a RunnableSpecification object.
		 * 
		 * @param runnable
		 */
		public RunnableSpecification(Class<? extends Runnable> runnable) {
			if (runnable == null) {
				throw new IllegalArgumentException("runnable must not be null");
			}
			this.runnableCls = runnable;
			this.amount = 1;
		}

		/**
		 * @return the runnable class
		 */
		public synchronized final Class<? extends Runnable> getRunnableCls() {
			return runnableCls;
		}

		/**
		 * @return the amount
		 */
		public synchronized final int getAmount() {
			return amount;
		}

	}

	/**
	 * Takes an array of RunnableSpecification and returns an array of Andrews
	 * processes.
	 * 
	 * @param runnableSpecification
	 *            an array of RunnableSpecification
	 * @return an array of Adrews process identities
	 * @throws InstantiationException
	 *             @see Thread(Runnable) constructor
	 * @throws IllegalAccessException
	 *             @see Thread(Runnable) constructor
	 */
	public static AndrewsProcess[] andrewsProcessFactory(
			RunnableSpecification[] runnableSpecification)
			throws InstantiationException, IllegalAccessException {
		final int n = Arrays.asList(runnableSpecification).stream().mapToInt(r->r.getAmount()).sum();
		AndrewsProcess result[] = new AndrewsProcess[n];
		int i = 0;
		for (RunnableSpecification rs : runnableSpecification) {
			for (int j = 0; j < rs.getAmount(); ++j) {
				final Class<? extends Runnable> cls = rs.getRunnableCls();
				result[i] = new AndrewsProcess(cls.newInstance());
				if (result[i].getRunnable() instanceof Rendezvous) {
					((Rendezvous) (result[i].getRunnable())).initialize();
					
				}
				++i;
			}
		}
		return result;
	}

	private static Runnable newInstance() {
		return null;
	}

	/**
	 * Starts the processes in the array of Andrews processes.
	 * 
	 * @param process an array of AndrewsProcess objects.
	 */
	public static void startAndrewsProcesses(AndrewsProcess process[]) {
		for (int i = 0; i < process.length; ++i) {
			process[i].start();
		}
	}

	/*
	 * Initializes internal static structures to keep track of mapping of
	 * Andrews processes to Threads and vice versa. Further, it also keeps track
	 * of identities relative to type (Runnable class).
	 */
	private final void initialize() {
		this.andrewsPid = nextAndrewsPid();
		AndrewsProcess.t2i.put(this, this.andrewsPid);
		ConcurrentHashMap<Thread, Integer> t2i = AndrewsProcess.c2t2i
				.get(this.cls);
		if (t2i == null) {
			t2i = new ConcurrentHashMap<Thread, Integer>();
			c2t2i.put(this.cls, t2i);
		}
		Integer nextRelativeId;
		if (t2i.isEmpty()) {
			nextRelativeId = 0;
		} else {
			nextRelativeId = t2i.values().stream().max(Integer::compare).get() + 1;
		}
		t2i.put(this, nextRelativeId);

		this.relativeToTypeAndrewsPid = nextRelativeId;
	}
	/**
	 * Always throws IllegalStateException to hinder students from extending this class and
	 * enforce the use of Runnable interface.
	 * @exception IllegalStateException 
	 */

	public AndrewsProcess() {
		super();
		throw new IllegalStateException(
				"In the IT325G course, only threads based on Runnable are allowed");
	}
	/**
	 * Invokes the super constructor and initializes the class.
	 * @exception IllegalStateException see Thread
	 */

	public AndrewsProcess(Runnable target) {
		super(target);
		this.cls = target.getClass();
		this.runnable = target;
		this.initialize();

	}

	/**
	 * Always throws IllegalStateException to hinder students from extending this class and
	 * enforce the use of Runnable interface.
	 * @exception IllegalStateException 
	 */
	public AndrewsProcess(String name) {
		super(name);
		throw new IllegalStateException(
				"In the IT325G course, only threads based on Runnable are allowed");

	}

	/**
	 * Invokes the super constructor and initializes the class.
	 * @exception IllegalStateException see Thread
	 */
	public AndrewsProcess(ThreadGroup group, Runnable target) {
		super(group, target);
		this.cls = target.getClass();
		this.runnable = target;
		this.initialize();

	}

	/**
	 * Invokes the super constructor and initializes the class.
	 * @exception IllegalStateException see Thread
	 */
	public AndrewsProcess(ThreadGroup group, String name) {
		super(group, name);
		throw new IllegalStateException(
				"In the IT325G course, only threads based on Runnable are allowed");

	}

	/**
	 * Invokes the super constructor and initializes the class.
	 * @exception IllegalStateException see Thread
	 */
	public AndrewsProcess(Runnable target, String name) {
		super(target, name);
		this.cls = target.getClass();
		this.runnable = target;
		this.initialize();

	}

	/**
	 * Invokes the super constructor and initializes the class.
	 * @exception IllegalStateException see Thread
	 */
	public AndrewsProcess(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		this.cls = target.getClass();
		this.runnable = target;
		this.initialize();

	}

	/**
	 * Invokes the super constructor and initializes the class.
	 * @exception IllegalStateException see Thread
	 */
	public AndrewsProcess(ThreadGroup group, Runnable target, String name,
			long stackSize) {
		super(group, target, name, stackSize);
		this.cls = target.getClass();
		this.runnable = target;
		this.initialize();

	}

	/**
	 * 
	 * @return Current global process identity within program scope.
	 */
	public static int currentAndrewsProcessId() {
		if (Thread.currentThread() == null) {
			throw new IllegalStateException(
					"Thread does not exist yet, cannot determine process identity");
		}
		final Integer result = t2i.get(Thread.currentThread());
		if (result == null) {
			throw new IllegalStateException(
					"Thread does not exist yet, cannot determine process identity");
		}
		return result;
	}

	/**
	 * Returns the process identity (integer) relative to the type (class of
	 * Runnable), for example, Reader, Writer in readers writers
	 * 
	 * @return an integer
	 * @exception IllegalStateException
	 */

	public static int currentRelativeToTypeAndrewsProcessId() {
		if (Thread.currentThread() == null) {
			throw new IllegalStateException(
					"Thread does not exist yet, cannot determine process identity");
		}
		final ConcurrentHashMap<Thread, Integer> t2i = AndrewsProcess.c2t2i
				.get(((AndrewsProcess) Thread.currentThread()).cls);
		if (t2i == null) {
			throw new IllegalStateException(
					"Thread does not exist yet, cannot determine process identity");
		}
		final Integer result = t2i.get(Thread.currentThread());
		if (result == null) {
			throw new IllegalStateException(
					"Thread does not exist yet, cannot determine process identity");
		}
		return result;
	}

	/**
	 * Returns the AndrewsProcess object representing the Andrews process.
	 * 
	 * @return AndrewsProcess.
	 * @exception IllegalStateException
	 *                in case the thread is not an AndewsProcess.
	 */
	public static AndrewsProcess currentAndrewsProcess() {
		final Thread t = Thread.currentThread();
		if (!(t instanceof AndrewsProcess)) {
			throw new IllegalStateException(
					"The current thread is not and AndrewsProcess");
		}
		return (AndrewsProcess) t;
	}
	
	/**
	 * 
	 * @return Returns the license text.
	 */

	public static String licenseText() {
		return "ConcurrentProgrammingExamplesInJava Copyright (C) 2017  Jonas Mikael Mellin\n"
				+ "This program comes with ABSOLUTELY NO WARRANTY; for details, \n"
				+ "see http://www.gnu.org/licenses/ if you have not received the LICENSE file.\n"
				+ "This is free software, and you are welcome to redistribute it\n"
				+ "under certain conditions\n";
	}

	/**
	 * Hides away the interrupt exception and sleeps (at least) for the specified amount
	 * time.
	 * 
	 * @param millis
	 *            the number milliseconds that the process should delay. Note,
	 *            this is the minimum amount of time.
	 */

	public static void uninterruptibleMinimumDelay(int millis) {
		long initialMillis = System.currentTimeMillis();
		long difference = millis + initialMillis - System.currentTimeMillis();
		while (difference > 0) {
			try {
				Thread.sleep(difference);
			} catch (InterruptedException unimportant) {
				AndrewsProcess.defaultInterruptedExceptionHandling(unimportant);
			}
			difference = millis + initialMillis - System.currentTimeMillis();
		}
	}

	/**
	 * @return the runnable
	 */
	public synchronized final Runnable getRunnable() {
		return runnable;
	}


	
	public static void  defaultInterruptedExceptionHandling(final InterruptedException interruptedException) {
		System.err.println("Thread interrupted, exiting");
		System.exit(0);
	}

}
