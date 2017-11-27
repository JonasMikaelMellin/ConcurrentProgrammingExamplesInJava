//    IT325G - Concurrent programming examples in Java
//    Copyright (C) 2017  Jonas Mikael Mellin
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package se.his.iit.it325g.examples.rendezvous.readersWriters;





import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsProcess.RunnableSpecification;
import se.his.iit.it325g.common.rendezvous.Entry;


public class GlobalProgramState {
	public static final int numberOfReaders = 5;
	public static final int numberOfWriters = 5;
	public static int numberOfIterations=10;
	public static ReaderWriterAccess readerWriterAccess;
	public static void main(String argv[]) {
		
		System.out.print(AndrewsProcess.licenseText());


		RunnableSpecification rs[]=new RunnableSpecification[3];
		rs[0]=new RunnableSpecification(ReaderWriterAccessServer.class,1);
		rs[1]=new RunnableSpecification(Reader.class,numberOfReaders);
		rs[2]=new RunnableSpecification(Writer.class,numberOfWriters);
		try {
			AndrewsProcess process[]=AndrewsProcess.andrewsProcessFactory(rs);
			readerWriterAccess=new ReaderWriterAccess(process[0]);
			AndrewsProcess.startAndrewsProcesses(process);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
