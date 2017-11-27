package se.his.iit.it325g.common.rendezvous;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import se.his.iit.it325g.common.AndrewsProcess;

public class RendezvousCallMonitorImplementation extends
		RendezvousCallImplementation {

	public RendezvousCallMonitorImplementation() {
		super();
	}

	@Override
	synchronized Result<?> call(Entry entry, Object[] parameter) {
		if (!entry.getRendezvous().equals(this.rendezvous)) {
			throw new IllegalArgumentException("Entry "+entry+" does not belong to rendezvous "+this);
		}

		Result<?> result=null;
		Queue<QueuedAndrewsProcess> eq=null;
		if (!entry.getGuard().evaluate(parameter)) {
			eq=this.rendezvous.s2eq.get(entry.getName());
			if (eq==null) {
				switch (entry.getOrdering().getOrder()) {
				case FIRST_COME_FIRST_SERVED:
					eq=new LinkedList<QueuedAndrewsProcess>();
					break;
				case ORDERED:
					eq=new PriorityQueue<QueuedAndrewsProcess>(entry.getOrdering().getComparator());
					break;
				default:
					throw new IllegalArgumentException("The alternatives "+entry.getOrdering().getOrder()+" is not implemented");				
				}

				this.rendezvous.s2eq.put(entry.getName(), eq);
			}
			eq.add(new QueuedAndrewsProcess(null,AndrewsProcess.currentAndrewsProcess(), parameter));
			boolean guardFlag=entry.getGuard().evaluate(parameter);
			while (!guardFlag || (guardFlag && !AndrewsProcess.currentAndrewsProcess().equals(eq.peek().getAndrewsProcess()))) {
				try {
					this.wait();
				} catch (InterruptedException interruptedException) {
					AndrewsProcess.defaultInterruptedExceptionHandling(interruptedException);

				}
				guardFlag=entry.getGuard().evaluate(parameter);
			}
		}
		if (eq!=null && AndrewsProcess.currentAndrewsProcess().equals(eq.peek().getAndrewsProcess())) {
			eq.poll();
		}
		entry.getAction().evaluate(parameter);
		this.notifyAll();
		result=entry.getAction().getResult();
		return result;

	}

	@Override
	public void run() {
		
	}

}
