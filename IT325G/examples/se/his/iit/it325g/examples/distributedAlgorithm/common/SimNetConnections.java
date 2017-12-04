package se.his.iit.it325g.examples.distributedAlgorithm.common;

import java.util.HashMap;
import java.util.Random;
import java.util.spi.CurrencyNameProvider;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AsynchronousChan;

public class SimNetConnections<R> {
	private int numberOfNodes;
	private double connectionThresholdAjdustment;
	private boolean connection[][];
	private double centrality[];
	private int numberOfOutgoing[];
	private int numberOfIncoming[];
	private HashMap<Integer,AsynchronousChan<R>> channel=new HashMap<Integer,AsynchronousChan<R>>();
	private Graph theTruth;

	public SimNetConnections(int numberOfNodes, double connectionThresholdAdjustment, long seed) {
		Random r=new Random(seed);
		this.theTruth = new SingleGraph("The truth");
		this.numberOfNodes = numberOfNodes;
		this.connectionThresholdAjdustment = connectionThresholdAdjustment;
		this.connection = new boolean[numberOfNodes][numberOfNodes];
		this.centrality = new double[numberOfNodes];
		this.numberOfOutgoing = new int[numberOfNodes];
		this.numberOfIncoming = new int[numberOfNodes];
		for (int i=0; i<numberOfNodes; ++i) {
			this.centrality[i]=r.nextDouble();
			Node n = this.theTruth.addNode(Integer.toString(i));
			n.addAttribute("ui.label", "Node: "+ Integer.toString(i)+", weight: "+String.format("%4.3f", this.getCentrality(i)));
		}
		this.theTruth.addAttribute("ui.title","The truth");
		this.theTruth.display();
		for (int i=0; i<numberOfNodes; ++i) {
			this.channel.put(i, new AsynchronousChan<R>());
			for (int j=0; j<numberOfNodes; ++j) {
				if (i>=j)  continue;
					
				if (r.nextDouble()<connectionThresholdAdjustment*this.centrality[i]*(((double)(numberOfNodes-this.numberOfOutgoing[i]))/(double)numberOfNodes)*(((double)(numberOfNodes-this.numberOfIncoming[i]))/(double)numberOfNodes)) {
					this.connection[i][j]=true;
					this.connection[j][i]=true;
					++this.numberOfOutgoing[i];
					++this.numberOfIncoming[j];
					++this.numberOfOutgoing[j];
					++this.numberOfIncoming[i];
					try {
						Edge edge=this.theTruth.addEdge(Integer.toString(i)+"-"+Integer.toString(j), Integer.toString(i), Integer.toString(j));
						edge.addAttribute("ui.label", Integer.toString(i)+"-"+Integer.toString(j));
					} catch (IdAlreadyInUseException iaiue) {
						// carry on and ignore this
					}
				}
				
			}
		}
	}

	public HashMap<Integer, AsynchronousChan<R>> getConnections(int i) {
		HashMap<Integer,AsynchronousChan<R>> result=new HashMap<Integer,AsynchronousChan<R>>();
		for (int j=0; j<this.numberOfNodes; ++j) {
			if (this.connection[i][j]) {
				result.put(j, this.channel.get(j));
			}
		}
		return result;
	}
	
	public AsynchronousChan<R> getOwnChannel() {
		return this.channel.get(AndrewsProcess.currentRelativeToTypeAndrewsProcessId());
	};
	
	public int getNoOfIncomingChannels(int i) {
		
		return this.numberOfIncoming[AndrewsProcess.currentRelativeToTypeAndrewsProcessId()];
	}
	public double getCentrality(int i) {
		return this.centrality[i];
	}

}
