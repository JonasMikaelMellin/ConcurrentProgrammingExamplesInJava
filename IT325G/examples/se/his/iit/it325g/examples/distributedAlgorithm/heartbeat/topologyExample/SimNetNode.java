package se.his.iit.it325g.examples.distributedAlgorithm.heartbeat.topologyExample;

import java.util.HashMap;

import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AsynchronousChan;

public class SimNetNode implements Runnable {
	private HashMap<Integer,AsynchronousChan<HeartbeatMessage>> i2a;
	private boolean fragmentOfConnections[][];
	private AsynchronousChan<HeartbeatMessage> myChannel;
	private Graph fragmentGraph;
	
	public SimNetNode() {
		// initialize fragment of view of connections
		this.fragmentOfConnections=new boolean[GlobalProgramState.numberOfNodes][GlobalProgramState.numberOfNodes];


	}

	@Override
	public void run() {
		
		// get connections, this cannot be done in the constructor of a Runnable
		this.i2a=GlobalProgramState.simNetConnections.getConnections(AndrewsProcess.currentRelativeToTypeAndrewsProcessId());
		// set the known neighbours
		for (Integer id:i2a.keySet()) {
			this.fragmentOfConnections[AndrewsProcess.currentRelativeToTypeAndrewsProcessId()][id]=true;
		}
		// find own channel
		this.myChannel=GlobalProgramState.simNetConnections.getOwnChannel();
		// create a graph
		this.fragmentGraph = new SingleGraph("Net view of process"+AndrewsProcess.currentAndrewsProcessId());
		StringBuffer sb=new StringBuffer();
		for (int i=0; i<GlobalProgramState.numberOfNodes; ++i) {
			Node n=this.fragmentGraph.addNode(Integer.toString(i));
			n.addAttribute("ui.label", "Node: "+ Integer.toString(i)+", weight: "+String.format("%4.3f", GlobalProgramState.simNetConnections.getCentrality(i)));
			String style = "size:"+(int)50.0*GlobalProgramState.simNetConnections.getCentrality(i)+";";
			double colorValue=255.0*GlobalProgramState.simNetConnections.getNoOfIncomingChannels(i)/(double)GlobalProgramState.numberOfNodes+100;
			int colorValueInt=(colorValue>255.0?255:(int) colorValue);
			String colorStyle="fill-color: #"+String.format("%02x",colorValueInt)+"0000;";
			System.out.println(colorStyle);
			sb.append("node#"+Integer.toString(i)+" { "+style+colorStyle+"}");
		}
		System.out.println(sb.toString());
		this.fragmentGraph.addAttribute("ui.style", sb.toString());
		this.fragmentGraph.display();
		this.fragmentGraph.addAttribute("ui.title", "Net view of process "+AndrewsProcess.currentAndrewsProcessId());

		for (int i=0; i<GlobalProgramState.numberOfNodes; ++i) {
			// push out heartbeat messages
			for (Integer id:i2a.keySet()) {
				AsynchronousChan<HeartbeatMessage> chan=i2a.get(id);
				chan.send(new HeartbeatMessage(this.fragmentOfConnections));
			}
			// consume messages received here, one for each incoming channel
			for (int j=0; j<GlobalProgramState.simNetConnections.getNoOfIncomingChannels(AndrewsProcess.currentRelativeToTypeAndrewsProcessId());++j) {
				HeartbeatMessage hr=this.myChannel.receive();
				AndrewsProcess.uninterruptibleMinimumDelay(1000);

				// for each message, update own fragment
				for (int k=0; k<GlobalProgramState.numberOfNodes; ++k) {
					for (int l=0; l<GlobalProgramState.numberOfNodes; ++l) {
						if (!this.fragmentOfConnections[k][l] && hr.get(k,l)) {
							try {
								Edge edge=this.fragmentGraph.addEdge(Integer.toString(k)+"-"+Integer.toString(l), Integer.toString(k), Integer.toString(l));
								edge.addAttribute("ui.label", Integer.toString(k)+"-"+Integer.toString(l));
							} catch (IdAlreadyInUseException | EdgeRejectedException iaiue) {
								// happily ignore and continue
							}
						}
						this.fragmentOfConnections[k][l]|=hr.get(k,l);
						
					}
				}
				
			}
			AndrewsProcess.uninterruptibleMinimumDelay(1000);
		}	
	}

}
