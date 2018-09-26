package mainPackage.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import mainPackage.ComponentsCtrl;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class ProgressSceneCtrl {

    @FXML
    Label numFilesLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    ProgressIndicator progressIndicator;

    private float totalProgress = 0;
    private int numFiles = 0;
    private int numThreadWorked = 0;


    /**
     * This method is invoked every times the Scene is loaded.
     */
    @FXML
    private void initialize(){
        progressBar.setProgress(totalProgress);
        progressIndicator.setProgress(totalProgress);
    }

    public void progress(float pu){
        totalProgress += pu;
        numThreadWorked++;
        progressBar.setProgress(totalProgress);
        progressIndicator.setProgress(totalProgress);
        if(numThreadWorked == numFiles + 1 ){
            Platform.runLater(() -> ComponentsCtrl.showOutputSceneLayout());
        }
    }

    public void setNumberOfFiles(Integer x){
        numFiles = x;
        numFilesLabel.setText(ComponentsCtrl.getDataCtrl().getGUIStrings().getProgressNumFilesAnalized(x));
    }

}
