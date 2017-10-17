package se.his.iit.it325g.rendezvous.criticalSection;

import se.his.iit.it325g.common.rendezvous.Action;
import se.his.iit.it325g.common.rendezvous.Entry;
import se.his.iit.it325g.common.rendezvous.Guard;
import se.his.iit.it325g.common.rendezvous.GuardNone;
import se.his.iit.it325g.common.rendezvous.OrderingDefault;
import se.his.iit.it325g.common.rendezvous.Rendezvous;
import se.his.iit.it325g.common.rendezvous.Result;

public class CriticalSectionServer extends Rendezvous {
	
	private boolean locked=false;
	private Result<Boolean> result=new Result<Boolean>(true);

	public CriticalSectionServer() {
		super("Critical section server");
	}

	@Override
	public void run() {
		super.run();
		
	}

	@Override
	public void initialize() {
		
		// enter critical section entry
		// enterCriticalSection[!locked] -> locked:=true
		
		Guard enterCriticalSectionGuard = new Guard("enterCriticalSectionGuard",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Guard#evaluate()
			 */
			@Override
			public boolean evaluate() {
				return !locked;
			}
			
		};
		Action enterCriticalSectionAction=new Action("enterCriticalSectionAction",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate() {
				// set the locked flag to true
				locked=true;
				// set the result of the action to true
				this.setResult(result);
				// return that the evaluation of the action was successful
				return true;
			}
			
		};
		OrderingDefault orderingDefault=new OrderingDefault(this);
		Entry enterCriticalSectionEntry=new Entry("enterCriticalSection",this,enterCriticalSectionGuard,enterCriticalSectionAction,orderingDefault) {
			@Override
			public Result<?> call(Object... object) {
				if (object.length>0) {
					throw new IllegalArgumentException("No parameters should be provided to entry enterCriticalSection");
				}
				return CriticalSectionServer.this.call(this);
			}
		};
		
		// exit critical section entry
		// exitCriticalSection -> locked:=false
		Action exitCriticalSectionAction=new Action("exitCriticalSectionAction",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate() {
				// set locked flag to false
				locked=false;
				// set the result of the action to true
				this.setResult(result);
				// return that the evaluation of the action was successful
				return true;
			}
			
		};
		Entry exitCriticalSectionEntry=new Entry("exitCriticalSection",this,new GuardNone(this),exitCriticalSectionAction,orderingDefault) {
			@Override
			public Result<?> call(Object... object) {
				if (object.length>0) {
					throw new IllegalArgumentException("No parameters should be provided to entry exitCriticalSection");
				}
				return CriticalSectionServer.this.call(this);
			}
		};
		
		
		
		
	}

}
