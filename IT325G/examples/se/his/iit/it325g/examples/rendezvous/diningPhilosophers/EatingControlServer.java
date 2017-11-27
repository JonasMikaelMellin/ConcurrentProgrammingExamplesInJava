package se.his.iit.it325g.examples.rendezvous.diningPhilosophers;

import se.his.iit.it325g.common.rendezvous.Action;
import se.his.iit.it325g.common.rendezvous.Entry;
import se.his.iit.it325g.common.rendezvous.Guard;
import se.his.iit.it325g.common.rendezvous.GuardNone;
import se.his.iit.it325g.common.rendezvous.OrderingDefault;
import se.his.iit.it325g.common.rendezvous.Rendezvous;
import se.his.iit.it325g.common.rendezvous.RendezvousCallMonitorImplementation;
import se.his.iit.it325g.common.rendezvous.Result;

public class EatingControlServer extends Rendezvous {
	
	static class LeftAndRight {
		private Integer left;
		private Integer right;
		public LeftAndRight(Integer index) {
			if (index==0) {
				left=index+1;
				right=GlobalProgramState.numberOfPhilosophers-1;
			} else {
				left=index-1;
				right=(index+1)%GlobalProgramState.numberOfPhilosophers;
			}
		}
		public final Integer getLeft() {
			return left;
		}
		public final Integer getRight() {
			return right;
		}
	}
	
	private boolean eating[];
	private Result<Boolean> result=new Result<Boolean>(true);

	public EatingControlServer() {
		super("Eating control server",new RendezvousCallMonitorImplementation());
		this.eating=new boolean[GlobalProgramState.numberOfPhilosophers];
		for (int i=0; i<GlobalProgramState.numberOfPhilosophers; ++i) {
			this.eating[i]=false;
		}
	}

	@Override
	public void run() {
		super.run();
		
	}
	
	private void checkParameter(Object[] parameter) {
		// check the input parameters, there is extremely limited compiler support
		// for variable argument lists
		if (parameter.length!=1) {
			throw new IllegalArgumentException("One and only one parameter should be passed to eatEntry");
		}
		if (!(parameter[0] instanceof Integer)) {
			throw new IllegalArgumentException("The parameter should be an integer");					
		}
		final Integer value=((Integer) parameter[0]);
		if (value<0 || value>GlobalProgramState.numberOfPhilosophers) {
			throw new IllegalArgumentException("The parameter should be an integer >=0 and <"+GlobalProgramState.numberOfPhilosophers);										
		}
	}
	

	@Override
	public void initialize() {
		super.initialize();
		
		// enter critical section entry
		// enterCriticalSection[!locked] -> locked:=true
		
		Guard enterEating_Guard = new Guard("enterEating_Guard",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Guard#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				final Integer index=(Integer)parameter[0];
				final LeftAndRight lar=new LeftAndRight(index);
				
				return !eating[lar.getLeft()]&& !eating[lar.getRight()];
			}
			
		};
		Action enterEating_Action=new Action("enterEating_Action",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				final Integer index=(Integer)parameter[0];

				// set the locked flag to true
				eating[index]=true;
				// set the result of the action to true
				this.setResult(result);
				// return that the evaluation of the action was successful
				return true;
			}
			
		};
		OrderingDefault orderingDefault=new OrderingDefault(this);
		Entry enterEating_Entry=new Entry("enterEating_Entry",this,enterEating_Guard,enterEating_Action,orderingDefault) {
			
			@Override
			public Result<?> call(Object... object) {
				checkParameter(object);
				return EatingControlServer.this.call(this, object);
			}
		};
		
		// exit critical section entry
		// exitCriticalSection -> locked:=false
		Action exitEating_Action=new Action("exitEating_Action",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				final Integer index=(Integer)parameter[0];
				// set locked flag to false
				eating[index]=false;
				// set the result of the action to true
				this.setResult(result);
				// return that the evaluation of the action was successful
				return true;
			}
			
		};
		Entry exitEating_Entry=new Entry("exitEating_Entry",this,new GuardNone(this),exitEating_Action,orderingDefault) {
			@Override
			public Result<?> call(Object... object) {
				checkParameter(object);
				return EatingControlServer.this.call(this, object);
			}
		};
		
		
		
		
	}

}
