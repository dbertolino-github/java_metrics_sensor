package mainPackage.util;

import mainPackage.ComponentsCtrl;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class ProgressManager {

    private float progressUnit;
    private int numberOfFiles;

    public ProgressManager(){
        progressUnit = 0;
        numberOfFiles = 0;
    }

    public void calculateProgressUnit(){
        this.numberOfFiles = ComponentsCtrl.getDataCtrl().getDataFlow().getJavaFiles().size();
        float pu = (float) 1/(this.numberOfFiles + 2);
        this.progressUnit = pu;

    }

    public float getProgressUnit(){
        return this.progressUnit;
    }

    public int getNumberOfFiles(){
        return this.numberOfFiles;
    }

}
