package se.his.iit.it325g.examples.distributedAlgorithm.probeAndEcho.topologyExample;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AsynchronousChan;

public class SimNetNode implements Runnable {
	private HashMap<Integer, AsynchronousChan<Message>> i2a;
	private boolean fragmentOfConnections[][];
	private AsynchronousChan<Message> myChannel;
	private Graph fragmentGraph;
	
	public SimNetNode() {
		// initialize fragment of view of connections
		this.fragmentOfConnections=new boolean[GlobalProgramState.numberOfNodes][GlobalProgramState.numberOfNodes];


	}
	private void addProbe(HashMap<Integer,HashSet<Probe>> i2sop, int id, Probe probe) {
		HashSet<Probe> tmp=i2sop.get(id);
		if (tmp==null) {
			tmp = new HashSet<Probe>();
			i2sop.put(id, tmp);
		}
		tmp.add(probe);
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
		for (int i=0; i<GlobalProgramState.numberOfNodes; ++i) {
			Node n=this.fragmentGraph.addNode(Integer.toString(i));
			n.addAttribute("ui.label", "Node: "+ Integer.toString(i)+", weight: "+String.format("%4.3f", GlobalProgramState.simNetConnections.getCentrality(i)));
		}
		this.fragmentGraph.display();
		this.fragmentGraph.addAttribute("ui.title", "Net view of process "+AndrewsProcess.currentAndrewsProcessId());
		HashMap<Integer,HashSet<Probe>> sentTo = new HashMap<Integer,HashSet<Probe>>();
		HashMap<Probe,Integer> receivedFrom = new HashMap<Probe,Integer>();
		if (AndrewsProcess.currentRelativeToTypeAndrewsProcessId()==GlobalProgramState.initiatingProcess) {
			// push out probe messages
			for (Integer id:i2a.keySet()) {
				AsynchronousChan<Message> chan=i2a.get(id);
				final Probe probe=new Probe(null,AndrewsProcess.currentRelativeToTypeAndrewsProcessId(),null);
				chan.send(probe);
				this.addProbe(sentTo, id, probe);
				receivedFrom.put(probe, GlobalProgramState.initiatingProcess);
			}
		}
		int noOfEchoesOriginatingFromThisProcess=0;
		while (noOfEchoesOriginatingFromThisProcess<i2a.size()) {
			Message msg = this.myChannel.receive();
			
			if (msg.getType()==Message.Type.PROBE) {
				final Probe probe = (Probe)msg;
				int resentProbes=0;
				for (int id:this.i2a.keySet()) {
					final HashSet<Probe> setOfSentProbes = sentTo.get(id);
					if (!(probe.isInPedigree(id))||(setOfSentProbes!=null && !setOfSentProbes.stream().anyMatch(p->p.getOriginator()==probe.getOriginator()))) {
						Probe replicatedProbe = new Probe(probe.getPedigree(),AndrewsProcess.currentRelativeToTypeAndrewsProcessId(),probe);
						this.i2a.get(id).send(replicatedProbe);
						this.addProbe(sentTo, id, replicatedProbe);
						receivedFrom.put(replicatedProbe,probe.getFrom());
						++resentProbes;
					}
				}
				if (resentProbes==0) { // this is the end node, send echo
					final Echo echo = new Echo(this.fragmentOfConnections,probe);
					i2a.get(probe.getFrom()).send(echo);
				}
			} else if (msg.getType()==Message.Type.ECHO) {
				final Echo echo = (Echo)msg;
				for (int i=0; i<GlobalProgramState.numberOfNodes; ++i) {
					for (int j=0; j<GlobalProgramState.numberOfNodes; ++j) {
						if (!this.fragmentOfConnections[i][j] && echo.get(i,j)) {
							try {
								Edge edge=this.fragmentGraph.addEdge(Integer.toString(i)+"-"+Integer.toString(j), Integer.toString(i), Integer.toString(j));
								edge.addAttribute("ui.label", Integer.toString(i)+"-"+Integer.toString(j));
							} catch (IdAlreadyInUseException | EdgeRejectedException iaiue) {
								// happily ignore and continue
							}
							
						}
						this.fragmentOfConnections[i][j]|=echo.get(i,j);

					}
				}
				// obtain the probe, which is the cause of the echo
				final HashSet<Probe> probeSet = sentTo.get(echo.getFrom());
				final Optional<Probe> probe = probeSet.stream().filter(p->p.getOriginator()==0).findFirst();
				if (probe.get().getOriginator()!=AndrewsProcess.currentRelativeToTypeAndrewsProcessId()) {
					// create an echo message 
					Echo newEcho = new Echo(this.fragmentOfConnections,probe.get());
					// send the echo back to the process that sent the probe
					this.i2a.get(receivedFrom.get(probe.get())).send(newEcho);
					// remove the sentTo indication
					sentTo.remove(echo.getFrom());
					// if there are no more replicated probes
					receivedFrom.remove(probe);
				}
			}

			AndrewsProcess.uninterruptibleMinimumDelay(1000);
		}	
	}

}
