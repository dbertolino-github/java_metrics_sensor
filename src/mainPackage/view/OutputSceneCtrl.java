package mainPackage.view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mainPackage.ComponentsCtrl;
import mainPackage.data.AllGeneralsPack;
import mainPackage.data.GeneralsPack;

import java.io.File;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class OutputSceneCtrl {

    @FXML
    private BarChart<String, Integer> chart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private Hyperlink printValuesLink;

    /**
     * This method is invoked every times the Scene is loaded.
     */
    @FXML
    private void initialize() throws InterruptedException {
        Boolean allMetricsBoolean = ComponentsCtrl.getDataCtrl().getDataFlow().getAllMetricsBoolean();
        GeneralsPack generalsPack = ComponentsCtrl.getDataCtrl().getDataFlow().getGeneralsPack();

        ObservableList<String> metrics = FXCollections.observableArrayList();
        metrics.add("tot LOC");
        metrics.add("tot Comments");
        metrics.add("tot Blank lines");
        metrics.add("tot NOM");
        metrics.add("tot NPM");
        if(allMetricsBoolean){
            metrics.add("max DIT");
        }
        xAxis.setCategories(metrics);


        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(metrics.get(0), generalsPack.getTotLOC()));
        series.getData().add(new XYChart.Data<>(metrics.get(1), generalsPack.getTotCommentLines()));
        series.getData().add(new XYChart.Data<>(metrics.get(2), generalsPack.getTotBlankLines()));
        series.getData().add(new XYChart.Data<>(metrics.get(3), generalsPack.getTotNOM()));
        series.getData().add(new XYChart.Data<>(metrics.get(4), generalsPack.getTotNPM()));
        if(allMetricsBoolean){
            AllGeneralsPack allGeneralsPack = (AllGeneralsPack) generalsPack;
            series.getData().add(new XYChart.Data<>(metrics.get(5), allGeneralsPack.getMaxDIT()));
        }
        chart.getData().add(series);
    }

    @FXML
    private void showPrintScene(){
        ComponentsCtrl.showPrintSceneLayout();
    }

}
