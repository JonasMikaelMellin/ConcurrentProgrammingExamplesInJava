package se.his.iit.it325g.multipleProducerConsumerSingleBuffer;

import java.util.concurrent.Semaphore;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;

public class GlobalState {
	public static int buffer;
	public static Semaphore empty=new Semaphore(1);
	public static Semaphore full=new Semaphore(0);

	public static void main(String argv[]) {
		RunnableSpecification rs[]=new RunnableSpecification[2];
		rs[0]=new RunnableSpecification(Producer.class,10);
		rs[1]=new RunnableSpecification(Consumer.class,20);
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
