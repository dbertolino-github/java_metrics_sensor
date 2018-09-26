package mainPackage.logic;

import mainPackage.data.ExtractorPack;
import mainPackage.data.CodeGeneralsPack;
import mainPackage.data.GeneralsPack;

import java.util.ArrayList;

/**
 * MetricsObserver Thread is launched when all the Extractors Thread have reached the Barrier.
 * Its scope is to elaborate all the collected data from the source code.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public abstract class MetricsObserver extends Thread {

	protected ArrayList<ExtractorPack> dataPacks;
	protected GeneralsPack generalsPack;

	/**
	 * Add an CodeExtractorPack from an MetricsExtractor Thread to the dataPacks ArrayList.
	 * @param pack
	 */
	public void addExtractorPack(ExtractorPack pack){
		this.dataPacks.add(pack);
	}


}
