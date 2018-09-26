package mainPackage.logic;

import mainPackage.ComponentsCtrl;
import mainPackage.data.CodeGeneralsPack;
import mainPackage.data.ExtractorPack;

import java.util.ArrayList;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class CodeObserver extends MetricsObserver{

    public CodeObserver(){
        this.dataPacks = new ArrayList<ExtractorPack>();
        this.generalsPack = new CodeGeneralsPack(this.dataPacks);
    }

    public void run(){
        this.generalsPack.computeGeneralVaulues();
        this.generalsPack.computeMOA();
        this.generalsPack.printResults();
        ComponentsCtrl.getDataCtrl().getDataFlow().setGeneralsPack(this.generalsPack);
        ComponentsCtrl.progress();
    }
}
