package se.his.iit.it325g.examples.rendezvous.readersWriters;

import se.his.iit.it325g.common.rendezvous.Action;
import se.his.iit.it325g.common.rendezvous.Entry;
import se.his.iit.it325g.common.rendezvous.Guard;
import se.his.iit.it325g.common.rendezvous.GuardNone;
import se.his.iit.it325g.common.rendezvous.OrderingDefault;
import se.his.iit.it325g.common.rendezvous.Rendezvous;
import se.his.iit.it325g.common.rendezvous.RendezvousCallImplementation;
import se.his.iit.it325g.common.rendezvous.RendezvousCallMonitorImplementation;
import se.his.iit.it325g.common.rendezvous.Result;

public class ReaderWriterAccessServer extends Rendezvous {
	
	private int numberOfReaders=0;
	private int numberOfWriters=0;
	
	private Result<Boolean> result=new Result<Boolean>(true);

	public ReaderWriterAccessServer() {
		super("Reader/writer access control server",new RendezvousCallMonitorImplementation());

	}

	@Override
	public void run() {
		super.run();
		
	}
	
	private void checkParameter(Object[] parameter) {
		// check the input parameters, there is extremely limited compiler support
		// for variable argument lists
		if (parameter.length>0) {
			throw new IllegalArgumentException("No parameters should be passed");
		}
	}
	

	@Override
	public void initialize() {
		super.initialize();
		
		// enter critical section entry
		// enterCriticalSection[!locked] -> locked:=true
		
		Guard acquireRead_Guard = new Guard("acquireRead_Guard",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Guard#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				
				return ReaderWriterAccessServer.this.numberOfWriters==0;
			}
			
		};
		Action acquireRead_Action=new Action("acquireRead_Action",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				++ReaderWriterAccessServer.this.numberOfReaders;
				// set the result of the action to true
				this.setResult(result);
				// return that the evaluation of the action was successful
				return true;
			}
			
		};
		OrderingDefault orderingDefault=new OrderingDefault(this);
		Entry acquireRead_Entry=new Entry("acquireRead_Entry",this,acquireRead_Guard,acquireRead_Action,orderingDefault) {
			
			@Override
			public Result<?> call(Object... object) {
				checkParameter(object);
				return ReaderWriterAccessServer.this.call(this, object);
			}
		};
		
		// exit critical section entry
		// exitCriticalSection -> locked:=false
		Action releaseRead_Action=new Action("releaseRead_Action",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				--ReaderWriterAccessServer.this.numberOfReaders;

				// set the result of the action to true
				this.setResult(result);
				// return that the evaluation of the action was successful
				return true;
			}
			
		};
		Entry releaseRead_Entry=new Entry("releaseRead_Entry",this,new GuardNone(this),releaseRead_Action,orderingDefault) {
			@Override
			public Result<?> call(Object... object) {
				checkParameter(object);
				return ReaderWriterAccessServer.this.call(this, object);
			}
		};
		
		
		

		//
		//
		Guard acquireWrite_Guard = new Guard("acquireWrite_Guard",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Guard#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				
				return ReaderWriterAccessServer.this.numberOfWriters==0 && ReaderWriterAccessServer.this.numberOfReaders==0;
			}
			
		};
		Action acquireWrite_Action=new Action("acquireWrite_Action",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				++ReaderWriterAccessServer.this.numberOfWriters;
				// set the result of the action to true
				this.setResult(result);
				// return that the evaluation of the action was successful
				return true;
			}
			
		};
		Entry acquireWrite_Entry=new Entry("acquireWrite_Entry",this,acquireWrite_Guard,acquireWrite_Action,orderingDefault) {
			
			@Override
			public Result<?> call(Object... object) {
				checkParameter(object);
				return ReaderWriterAccessServer.this.call(this, object);
			}
		};
		
		// exit critical section entry
		// exitCriticalSection -> locked:=false
		Action releaseWrite_Action=new Action("releaseWrite_Action",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				--ReaderWriterAccessServer.this.numberOfWriters;

				// set the result of the action to true
				this.setResult(result);
				// return that the evaluation of the action was successful
				return true;
			}
			
		};
		Entry releaseWrite_Entry=new Entry("releaseWrite_Entry",this,new GuardNone(this),releaseWrite_Action,orderingDefault) {
			@Override
			public Result<?> call(Object... object) {
				checkParameter(object);
				return ReaderWriterAccessServer.this.call(this, object);
			}
		};
		

	}

}
