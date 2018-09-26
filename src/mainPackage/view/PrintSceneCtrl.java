package mainPackage.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mainPackage.ComponentsCtrl;
import mainPackage.data.GeneralsPack;

import java.io.File;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class PrintSceneCtrl {

    @FXML
    private Hyperlink directoryChooser;
    @FXML
    private TextField outPutDir;
    @FXML
    private TextField outPutName;
    @FXML
    private Hyperlink printLink;

    private File outPutFileObject;

    /**
     * This method is invoked every times the Scene is loaded.
     */
    @FXML
    private void initialize() throws InterruptedException {
    }

    /**
     * This method is the listener method for the directoryChooserLink and it opens a directory chooser window
     * to select the directory to the source code.
     */
    @FXML
    private void chooseOutput() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose output directory");
        outPutFileObject = directoryChooser.showDialog(new Stage());
        if(outPutFileObject != null) {
            outPutDir.setText(outPutFileObject.getAbsolutePath());
            outputApprovedScene();
        } else{
            outputNotApprovedScene();
        }
    }

    @FXML
    private void print(){
        Alert alert = new Alert(Alert.AlertType.WARNING);

        String fileName = outPutName.getText();
        if(fileName != null || !fileName.equals("")){

            GeneralsPack generalsPack = ComponentsCtrl.getDataCtrl().getDataFlow().getGeneralsPack();
            ComponentsCtrl.getCsvManager().createFiles(outPutFileObject, outPutName.getText());

            if(ComponentsCtrl.getDataCtrl().getDataFlow().getAllMetricsBoolean()){
                ComponentsCtrl.getCsvManager().writeTotValuesAll(generalsPack);
                ComponentsCtrl.getCsvManager().writeClassValuesAll(generalsPack);
            }else{
                ComponentsCtrl.getCsvManager().writeTotValues(generalsPack);
                ComponentsCtrl.getCsvManager().writeClassValues(generalsPack);
            }
            ComponentsCtrl.getCsvManager().writeFunctionValues(generalsPack);
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation dialog");
            alert.setHeaderText("Files successfully created!");
            alert.setContentText("Control your output directory.");
            alert.showAndWait();
            ComponentsCtrl.showOutputSceneLayout();


        }else{
            alert.setTitle("Warning dialog");
            alert.setHeaderText("Invalid project name!");
            alert.setContentText("Set a project name for the files to be printed.");
            alert.showAndWait();
        }
    }

    /**
     * This method modify the look of the Scene in the UI and create a warning window.
     */
    private void outputApprovedScene(){
        outPutDir.setText(outPutFileObject.getAbsolutePath());
        outPutDir.getStyleClass().remove("notApproved");
        outPutDir.getStyleClass().add("approved");
        printLink.setDisable(false);
    }

    /**
     * This method modify the look of the Scene in the UI and create a warning window.
     */
    private void outputNotApprovedScene(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning dialog");
        alert.setHeaderText("Invalid directory!");
        alert.setContentText("Cannot choose this directory.");

        outPutDir.setText("");
        outPutDir.getStyleClass().remove("approved");
        outPutDir.getStyleClass().add("notApproved");
        printLink.setDisable(true);
        alert.showAndWait();
    }
}
