import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.his.iit.it325g.common.AndrewsProcess;


public class AndrewsProcessConstructors {
	
	public static class TestProcess extends AndrewsProcess {
		TestProcess() {
			super();
		}
	}
	
	public static class TestProcess2 extends AndrewsProcess {
		TestProcess2() {
			
		}
	}
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			AndrewsProcess andrewsProcess = new AndrewsProcess();
			fail("Andrews process created");
		} catch (IllegalStateException ise) {
		}
		
		try {
			AndrewsProcess andrewsProcess = new AndrewsProcess() {
				void AndrewsProcess() {
					
				}
			};
			fail("Andrews process created");
		} catch (IllegalStateException ise) {
			
		}
		try {
			AndrewsProcess andrewsProcess = new TestProcess();
			fail("Andrews process created");
		} catch (IllegalStateException ise) {
			
		}
		try {
			AndrewsProcess andrewsProcess = new TestProcess2();
			fail("Andrews process created");
		} catch (IllegalStateException ise) {
			
		}
		
		Runnable runnable=new Runnable() {

			@Override
			public void run() {
				
			}
			
		};
		
		try {
			AndrewsProcess andrewsProcess = new AndrewsProcess(runnable);
			andrewsProcess.start();
			andrewsProcess.join();
		} catch (IllegalStateException ise) {
			fail("Failed to create an AndrewsProcess");
		} catch (InterruptedException e) {
			fail("Failed to join an AndrewsProcess due to a interrupts");
		}
	}

}
