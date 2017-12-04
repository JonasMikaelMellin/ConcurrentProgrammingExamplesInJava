package se.his.iit.it325g.examples.distributedAlgorithm.heartbeat.topologyExample;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;
import se.his.iit.it325g.examples.distributedAlgorithm.common.SimNetConnections;

public class GlobalProgramState {
	public static volatile int numberOfNodes=20;
	public static final double connectionThresholdAdjustment=0.5;
	public static final long seed=1L;
	public static volatile SimNetConnections<HeartbeatMessage> simNetConnections=new SimNetConnections<HeartbeatMessage>(numberOfNodes,connectionThresholdAdjustment,seed);
	
	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());

		RunnableSpecification rs[]=new RunnableSpecification[1];
		rs[0]=new RunnableSpecification(SimNetNode.class,numberOfNodes);
		try {
			AndrewsProcess process[]=AndrewsProcess.andrewsProcessFactory(rs);
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}


}
