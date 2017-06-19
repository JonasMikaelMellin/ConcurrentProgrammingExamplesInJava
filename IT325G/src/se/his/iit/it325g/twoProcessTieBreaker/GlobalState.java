package se.his.iit.it325g.twoProcessTieBreaker;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;

public class GlobalState {
	
	static boolean in1=false;
	static boolean in2=false;
	static int last=1;


	public static void main(String argv[]) {
		RunnableSpecification rs[]=new RunnableSpecification[2];
		AndrewsProcess[] process;
		try {
			rs[0]=new RunnableSpecification(TwoProcessTieBreakerRunnable1.class,1);
			rs[1]=new RunnableSpecification(TwoProcessTieBreakerRunnable2.class,1);
			process = AndrewsProcess.andrewsProcessFactory(rs);
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
