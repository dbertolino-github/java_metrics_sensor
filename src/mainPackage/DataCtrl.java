package mainPackage;

import mainPackage.util.DataFlow;
import mainPackage.util.GUIStrings;
import mainPackage.util.ProgressManager;
import mainPackage.view.ProgressSceneCtrl;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class DataCtrl {

    private GUIStrings guiStrings;
    private DataFlow dataFlow;

    public DataCtrl(){
        guiStrings = new GUIStrings();
        dataFlow  = new DataFlow();
    }

    public GUIStrings getGUIStrings(){
        return guiStrings;
    }

    public DataFlow getDataFlow() {
        return dataFlow;
    }

}
