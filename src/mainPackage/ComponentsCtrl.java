package mainPackage;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainPackage.util.*;
import mainPackage.view.ProgressSceneCtrl;

/**
 * ComponentsCtrl hosts the main method, controls all the UI's scenes and contains a final instance of the DataFlow Class.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class ComponentsCtrl extends Application {
	
	private static Stage primaryStage;
	private static BorderPane structureLayout;
	private static VBox inputLayout;
	private static VBox progressLayout;
	private static VBox resultLayout;
	private static VBox printLayout;
	private static ProgressSceneCtrl progressController;
	private static ProgressManager progressManager;
	private static DataCtrl dataCtrl;
	private static CsvManager csvManager;

	public static void main(String[] args)  {
		progressManager = new ProgressManager();
		dataCtrl = new DataCtrl();
		csvManager = new CsvManager();
		launch(args);
	}

	public static DataCtrl getDataCtrl(){
		return dataCtrl;
	}

	public static CsvManager getCsvManager() {
		return  csvManager;
	}

	public static void progress(){
		float x = progressManager.getProgressUnit();
		progressController.progress(x);
	}

	@Override
	public void start(Stage ps) {
		try {
			ComponentsCtrl.primaryStage = ps;
			ComponentsCtrl.primaryStage.setTitle("JMS");
			ComponentsCtrl.primaryStage.setResizable(false);
	        showStructureLayout();
	        showInputSceneLayout();
			primaryStage.show();
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		
	}

	/**
	 * This method load a BorderPane as the Scene for the primary stage of the UI.
	 */
	public static void showStructureLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ComponentsCtrl.class.getResource("view/StructureLayout.fxml"));
			structureLayout = loader.load();

			Scene scene = new Scene(structureLayout);
			primaryStage.setScene(scene);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * This method set set as the center of the structure layout BorderPane, a VBox which represents
	 * the scene for the input request.
	 */
	public static void showInputSceneLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ComponentsCtrl.class.getResource("view/InputScene.fxml"));
			inputLayout = loader.load();

			structureLayout.setCenter(inputLayout);
		} catch (IOException e){
			e.printStackTrace();
		}

	}

	/**
	 * This method set as the center of the structure layout BorderPane, a VBox which represents
	 * the scene for the input request.
	 */
	public static void showProgressSceneLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ComponentsCtrl.class.getResource("view/ProgressScene.fxml"));
			progressLayout = loader.load();
			progressController = (ProgressSceneCtrl) loader.getController();
			progressManager.calculateProgressUnit();
			progressController.setNumberOfFiles(progressManager.getNumberOfFiles());
			structureLayout.setCenter(progressLayout);

		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * This method set as the center of the structure layout BorderPane, a VBox which represents
	 * the scene for the input request.
	 */
	public static void showOutputSceneLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ComponentsCtrl.class.getResource("view/OutputScene.fxml"));
			resultLayout = loader.load();

			structureLayout.setCenter(resultLayout);
		} catch (IOException e){
			e.printStackTrace();
		}

	}

	/**
	 * This method set as the center of the structure layout BorderPane, a VBox which represents
	 * the scene for the input request.
	 */
	public static void showPrintSceneLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ComponentsCtrl.class.getResource("view/PrintScene.fxml"));
			printLayout = loader.load();

			structureLayout.setCenter(printLayout);
		} catch (IOException e){
			e.printStackTrace();
		}

	}


}
