package mainPackage.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mainPackage.ComponentsCtrl;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class GUIStrings {

    private ObservableList<String> codeMetricsList;
    private ObservableList<String> allMetricsList;
    private String progressNumFilesAnalized;

    public GUIStrings(){
        codeMetricsList = FXCollections.observableArrayList (
                "LOC: per class, function and total",
                "COMMENTS: per class and total",
                "EMPTY LINES: per class and total.",
                "NOM: per class and total.",
                "NPM: per class and total.",
                "AMC: per class",
                "WMC: where CC is the complexity considered.",
                "CC: per function and average per class. ",
                "CAM: per class",
                "DAM: per class.",
                "MOA: per class and average.",
                "RFC: response per class",
                "LCOM3: per class");

        allMetricsList = FXCollections.observableArrayList (
                "LOC: per class, function and total",
                "COMMENTS: per class and total",
                "EMPTY LINES: per class and total.",
                "NOM: per class and total.",
                "NPM: per class and total.",
                "AMC: per class",
                "WMC: where CC is the complexity considered.",
                "CC: per function and average per class. ",
                "CAM: per class",
                "DAM: per class.",
                "MOA: per class and average.",
                "RFC: response per class",
                "LCOM3: per class",
                "CBO: per class",
                "DIT: per class, and max value.",
                "NOC: per class." );

        progressNumFilesAnalized = "analysing _ classes/interfaces";

    }

    public ObservableList<String> getCodeMetrics() {
        return codeMetricsList;
    }

    public ObservableList<String> getAllMetrics() {
        return allMetricsList;
    }

    public String getProgressNumFilesAnalized(Integer num){
        return progressNumFilesAnalized.replaceFirst("_", num.toString());
    }
}
