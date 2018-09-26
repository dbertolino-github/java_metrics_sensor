package mainPackage.logic;

import mainPackage.ComponentsCtrl;
import mainPackage.data.AllGeneralsPack;
import mainPackage.data.ExtractorPack;

import java.util.ArrayList;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class AllObserver extends MetricsObserver {


    public AllObserver() {
        this.dataPacks = new ArrayList<ExtractorPack>();
        this.generalsPack = new AllGeneralsPack(this.dataPacks);
    }

    public void run() {
        this.generalsPack.computeGeneralVaulues();
        this.generalsPack.computeMOA();
        this.generalsPack.computeMaxDIT();
        this.generalsPack.computeNOC();
        this.generalsPack.computeCBO();
        this.generalsPack.printResults();
        ComponentsCtrl.getDataCtrl().getDataFlow().setAllMetricsBoolean();
        ComponentsCtrl.getDataCtrl().getDataFlow().setGeneralsPack(this.generalsPack);
        ComponentsCtrl.progress();
    }


}
