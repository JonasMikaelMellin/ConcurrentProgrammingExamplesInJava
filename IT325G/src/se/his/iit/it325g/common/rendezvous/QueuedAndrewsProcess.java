package se.his.iit.it325g.common.rendezvous;

import se.his.iit.it325g.common.AndrewsProcess;

public class QueuedAndrewsProcess {
	private Entry entry;
	private AndrewsProcess andrewsProcess;
	private Object[] parameter;
	/**
	 * @param entry TODO
	 * @param andrewsProcess
	 * @param parameter
	 */
	public QueuedAndrewsProcess(Entry entry,
			AndrewsProcess andrewsProcess, Object[] parameter) {
		this.andrewsProcess = andrewsProcess;
		this.parameter = parameter;
	}
	/**
	 * @return the andrewsProcess
	 */
	public synchronized final AndrewsProcess getAndrewsProcess() {
		return andrewsProcess;
	}
	/**
	 * @return the parameter
	 */
	public synchronized final Object[] getParameter() {
		return parameter;
	}
	/**
	 * @return the entry
	 */
	public synchronized final Entry getEntry() {
		return entry;
	}
	
	

}
