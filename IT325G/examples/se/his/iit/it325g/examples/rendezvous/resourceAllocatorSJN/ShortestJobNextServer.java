//    IT325G - Concurrent programming examples in Java
//    Copyright (C) 2017  Jonas Mikael Mellin
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package se.his.iit.it325g.examples.rendezvous.resourceAllocatorSJN;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.rendezvous.Action;
import se.his.iit.it325g.common.rendezvous.Entry;
import se.his.iit.it325g.common.rendezvous.Guard;
import se.his.iit.it325g.common.rendezvous.GuardNone;
import se.his.iit.it325g.common.rendezvous.Ordering;
import se.his.iit.it325g.common.rendezvous.OrderingDefault;
import se.his.iit.it325g.common.rendezvous.QueuedAndrewsProcess;
import se.his.iit.it325g.common.rendezvous.Rendezvous;
import se.his.iit.it325g.common.rendezvous.RendezvousCallImplementation;
import se.his.iit.it325g.common.rendezvous.RendezvousCallMonitorImplementation;
import se.his.iit.it325g.common.rendezvous.Result;
import se.his.iit.it325g.examples.rendezvous.criticalSection.CriticalSectionServer;
import se.his.iit.it325g.examples.rendezvous.readersWriters.ReaderWriterAccessServer;

/**¨
 * Conventional shortest job next solution in Java. 
 * @author melj
 *
 */

public class ShortestJobNextServer extends Rendezvous {
	protected ShortestJobNextServer(String name,
			RendezvousCallImplementation rendezvousCallImplementation) {
		super(name, rendezvousCallImplementation);
	}
	private int serverValue=0;
	private HashSet<Integer> unitSet=new HashSet<Integer>();
	private Result<Boolean> result=new Result<Boolean>(true);

	
	public ShortestJobNextServer() {
		super("Shortest Job Next Server",new RendezvousCallMonitorImplementation());
		this.unitSet.addAll(IntStream.range(0, GlobalProgramState.numberOfResources).boxed().collect(Collectors.toList()));
	}
	
	/* (non-Javadoc)
	 * @see se.his.iit.it325g.common.rendezvous.Rendezvous#initialize()
	 */
	@Override
	public void initialize() {
		super.initialize();
		
		Guard request_Guard = new Guard("request_Guard", this) {
			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Guard#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				
				return ShortestJobNextServer.this.unitSet.size()>0;
			}
		};
		
		Action request_Action = new Action("request_Action",this) {
			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				final Integer unit=ShortestJobNextServer.this.unitSet.iterator().next();
				ShortestJobNextServer.this.unitSet.remove(unit);
				
				this.setResult(new Result<Integer>(unit));
				
				return true;
			}
		};
		
		Ordering request_Ordering = new Ordering<QueuedAndrewsProcess>("request_Ordering",this,Order.ORDERED,new Comparator<QueuedAndrewsProcess>() {

			 

			@Override
			public int compare(QueuedAndrewsProcess o1, QueuedAndrewsProcess o2) {
				return ((Integer)o1.getParameter()[0]).compareTo((Integer)o2.getParameter()[0]);
			}

			
		});
		
		Entry request_Entry = new Entry("request_Entry",this,request_Guard,request_Action,request_Ordering) {
			@Override
			public Result<?> call(Object... object) {
				checkParameter(object);
				return ShortestJobNextServer.this.call(this, object);
			}
		};
		
		OrderingDefault orderingDefault=new OrderingDefault(this);
		Action release_Action=new Action("release_Action",this) {

			/* (non-Javadoc)
			 * @see se.his.iit.it325g.common.rendezvous.Action#evaluate()
			 */
			@Override
			public boolean evaluate(Object[] parameter) {
				// set locked flag to false
				ShortestJobNextServer.this.unitSet.add(((Integer)parameter[0]));
				// set the result of the action to true
				this.setResult(result);
				// return that the evaluation of the action was successful
				return true;
			}
			
		};
		Entry release_Entry=new Entry("release_Entry",this,new GuardNone(this),release_Action,orderingDefault) {
			@Override
			public Result<?> call(Object... object) {
				checkParameter(object);
				return ShortestJobNextServer.this.call(this, object);
			}
		};

		
		
	}
	protected void checkParameter(Object[] object) {
		if (object.length!=1) {
			throw new IllegalArgumentException("One and only one parameter is allowed.");
		}
		if (!(object[0] instanceof Integer)) {
			throw new IllegalArgumentException("The parameter is not an integer");
		}
		if (((Integer)object[0])<0) {
			throw new IllegalArgumentException("The parameter is less than zero");			
		}
	}

}
