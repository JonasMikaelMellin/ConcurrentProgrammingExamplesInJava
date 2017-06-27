package se.his.iit.it325g.nProcessBakeryAlgorithm;

import java.util.stream.IntStream;

import se.his.iit.it325g.common.AndrewsProcess;

public class GlobalProgramState {

	static int n=10;
	static int turn[]=IntStream.generate(() -> -1).limit(n).toArray(); // defaults to n * -1

	public static void main(String argv[]) {
		AndrewsProcess[] process;
		try {
			process = AndrewsProcess.andrewsProcessFactory(n, NProcessBakeryAlgorithmRunnable.class);
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
