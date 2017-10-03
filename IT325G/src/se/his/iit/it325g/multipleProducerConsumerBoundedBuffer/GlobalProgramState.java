package se.his.iit.it325g.multipleProducerConsumerBoundedBuffer;

import java.util.concurrent.Semaphore;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;

public class GlobalProgramState {
	public static int n=10;
	public static int buffer[]=new int[n];
	public static int front=0;
	public static int rear=0;
	public static Semaphore empty=new Semaphore(n);
	public static Semaphore full=new Semaphore(0);

	public static void main(String argv[]) {
		RunnableSpecification rs[]=new RunnableSpecification[2];
		rs[0]=new RunnableSpecification(Producer.class,1);
		rs[1]=new RunnableSpecification(Consumer.class,1);
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