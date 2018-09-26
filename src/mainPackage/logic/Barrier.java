package mainPackage.logic;

import mainPackage.data.ExtractorPack;

/**
 * Barrier class implements the java concurrency Thread Barrier practice.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class Barrier {
	
	private int need;
	private int arrived;
	private boolean releasing;
	private MetricsObserver reduce;

	/**
	 * Barrier constructor take as parameters the number of needed Threads and the last Thread to be launched.
	 * @param number
	 * @param r
	 */
	public Barrier(int number, MetricsObserver r){
		this.need = number;
		this.arrived = 0;
		this.releasing = false;
		this.reduce = r;
	}
	
	public synchronized int value() {
		return arrived;
	}
	
	public int capacity() {
		return need;
	}

	/**
	 * WaitB is called by a Thread to stop until all the required Threads have finished their  work.
	 * @throws InterruptedException
	 */
	public synchronized void waitB() throws InterruptedException {
		while (releasing) {
			wait();
		}
		try{
			arrived++;
			while(arrived != need && !releasing){
				wait();
			}
			if(arrived == need) {
				releasing = true;
				System.err.println("\nALL " + need +" FILES WERE SCANNED.\n");
				reduce.start();
				notifyAll();
			}
		}finally {
			arrived --;
			if (arrived == 0) {
				releasing = false;
				notifyAll();
			}
		}
	}

	/**
	 * This synchronized method lets the Threads to pass their ExtractorPacks of data to the reduce Thread, the MetricsObserver.
	 * @param pack
	 */
	public synchronized void addExtactorPack(ExtractorPack pack){
		reduce.addExtractorPack(pack);
	}

}
