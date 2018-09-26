package mainPackage.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mainPackage.ComponentsCtrl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class InputSceneCtrl {

    @FXML
    private Hyperlink srcChooserLink;
    @FXML
    private TextField srcDir;
    @FXML
    private Hyperlink jarChooserLink;
    @FXML
    private TextField jarDir;
    @FXML
    private ListView metricsList;
    @FXML
    private Hyperlink clearLink;
    @FXML
    private Hyperlink calculateMetricsLink;

    private Boolean srcChecked = false;
    private File srcFile;
    private Boolean jarChecked = false;
    private File choosenFile;
    private JarFile jarFile;
    private String jarPath;

    /**
     * This method is invoked every times the Scene is loaded.
     */
    @FXML
    private void initialize(){
        jarChooserLink.setDisable(true);
        jarDir.setDisable(true);
        clearLink.setDisable(true);
        calculateMetricsLink.setDisable(true);
    }

    /**
     * This method is the listener method for the directoryChooserLink and it opens a directory chooser window
     * to select the directory to the source code.
     */
    @FXML
    private void chooseSrc() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose src directory");
        srcFile = directoryChooser.showDialog(new Stage());
        if(srcFile != null){
            try {
                srcChecked = false;
                checkSrc(srcFile);
                if(srcChecked){
                    srcApprovedScene();
                    deleteJavaFiles();
                    getJavaFiles(srcFile);
                }
                else {
                    srcNotApprovedScene();
                    deleteJavaFiles();
                }

            } catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            srcNotApprovedScene();
            deleteJavaFiles();
        }

    }

    /**
     * This method is the listener method for the fileChooserLink and open a File chooser window to select the jar file.
     * It also controls that the chosen file is effectively a .jar file and invoke the checkJar method.
     */
    @FXML
    private void ChooseJar() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose jar file");
        choosenFile = fileChooser.showOpenDialog(new Stage());
        if(choosenFile != null){
            try {
                if(choosenFile != null && choosenFile.toString().endsWith(".jar")){
                    jarPath = choosenFile.getAbsolutePath();
                    jarFile = new JarFile(jarPath);
                    jarChecked = false;
                    checkJar(jarFile, jarPath);
                    if(jarChecked){
                        jarApprovedScene();
                    }
                    else{
                        jarNotApprovedScene();
                    }
                }
                else{
                    jarNotApprovedScene();
                }
            } catch(Exception e){e.printStackTrace();}
        }
        else{
            jarNotApprovedScene();
        }
    }

    /*
     *This method clear re-intialize the interface
     */
    @FXML
    private void clear(){
        jarChooserLink.setDisable(true);
        jarDir.setDisable(true);
        clearLink.setDisable(true);
        calculateMetricsLink.setDisable(true);
        srcDir.setText("");
        jarDir.setText("");
        metricsList.setItems(null);
        deleteJavaFiles();

    }

    /**
     * This is the listener method for the calculateMetrics HyperLink and it launches the metrics calculation
     * from the DataFlow in the ComponentsCtrl.
     */
    @FXML
    private void calculateMetrics(){
        srcDir.setDisable(true);
        srcChooserLink.setDisable(true);
        jarChooserLink.setDisable(true);
        jarDir.setDisable(true);
        clearLink.setDisable(true);
        calculateMetricsLink.setDisable(true);

        if(jarChecked){
            getClasses(jarFile, jarPath);
            Task<Void> computing = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    ComponentsCtrl.getDataCtrl().getDataFlow().calculateAllMetrics();
                    return null;
                }
            };
            ComponentsCtrl.showProgressSceneLayout();
            new Thread(computing).start();
        }
        else{
            Task<Void> computing = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    ComponentsCtrl.getDataCtrl().getDataFlow().calculateCodeMetrics();
                    return null;
                }
            };
            ComponentsCtrl.showProgressSceneLayout();
            new Thread(computing).start();

        }
    }

    /**
     * This method controls that the given directory contains at least one .java file to be analysed.
     * @param file
     * @throws Exception
     */
    private void checkSrc(File file) throws Exception{
        if(file.isDirectory() && file != null){
            File[] subFiles = file.listFiles();
            if(subFiles != null){
                for(File f : subFiles){
                    if(f != null && f.isFile() && f.toString().endsWith(".java")){
                        srcChecked = true;
                        break;
                    }
                    else if(f != null && f.isDirectory()){
                        checkSrc(f);
                    }
                }
            }

        }
    }

    /**
     * This method try to find a match between the given source code and the .class files inside the jar.
     * @param jar
     * @param jarPath
     */
    private void checkJar(JarFile jar, String jarPath) {
        try{
            Enumeration<JarEntry> e = jar.entries();
            URL[] urls = { new URL("jar:file:" + jarPath +"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                // -6 because of .class
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                Class<?> c = cl.loadClass(className);
                for(File f : ComponentsCtrl.getDataCtrl().getDataFlow().getJavaFiles()){
                    if(f.getPath().endsWith(c.getSimpleName() + ".java")){
                        jarChecked = true;
                        break;
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method modify the look of the Scene in the UI and create a warning window.
     */
    private void srcApprovedScene(){
        srcDir.setText(srcFile.getAbsolutePath());
        srcDir.getStyleClass().remove("notApproved");
        srcDir.getStyleClass().add("approved");
        calculateMetricsLink.setDisable(false);
        clearLink.setDisable(false);
        jarChooserLink.setDisable(false);
        jarDir.setDisable(false);
        metricsList.setVisible(true);
        metricsList.setItems(ComponentsCtrl.getDataCtrl().getGUIStrings().getCodeMetrics());
    }

    /**
     * This method modify the look of the Scene in the UI and create a warning window.
     */
    private  void srcNotApprovedScene(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning dialog");
        alert.setHeaderText("Invalid directory!");
        alert.setContentText("Directory is empty or simply do not contains .java files to analyse.");

        srcDir.getStyleClass().remove("approved");
        srcDir.getStyleClass().add("notApproved");
        srcDir.setText("");
        metricsList.setVisible(false);
        metricsList.setItems(null);
        calculateMetricsLink.setDisable(true);
        clearLink.setDisable(true);
        jarChooserLink.setDisable(true);
        jarDir.setDisable(true);
        alert.showAndWait();
    }

    /**
     * This method modify the look of the Scene in the UI.
     */
    private void jarApprovedScene(){
        jarDir.setText(choosenFile.getAbsolutePath());
        jarDir.getStyleClass().remove("notApproved");
        jarDir.getStyleClass().add("approved");
        metricsList.setItems(ComponentsCtrl.getDataCtrl().getGUIStrings().getAllMetrics());
    }

    /**
     * This method modify the look of the Scene in the UI and create a warning window.
     */
    private void jarNotApprovedScene(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning dialog");
        alert.setHeaderText("Invalid jar file!");
        alert.setContentText("Jar file is not valid or it not contains compiled classes of your source code.");

        jarDir.setText("");
        jarDir.getStyleClass().remove("approved");
        jarDir.getStyleClass().add("notApproved");
        metricsList.setItems(ComponentsCtrl.getDataCtrl().getGUIStrings().getCodeMetrics());
        alert.showAndWait();
    }

    /**
     * This methods search in the given directory for all .java files to analyse.
     * @param f
     */
    private void getJavaFiles(File f){

        if(f != null && f.isDirectory()){
            File[] subf = f.listFiles();
            for(File file : subf){
                getJavaFiles(file);
            }
        }
        else if(f != null && f.isFile() && f.toString().endsWith(".java")){
            ComponentsCtrl.getDataCtrl().getDataFlow().addJavaFile(f);
        }
    }

    /**
     * This method extracts all the .class files and loads the respectively Class objects to the DataFlow
     * in the ComponentsCtrl
     * @param jarFile
     * @param jarPath
     */
    private void getClasses(JarFile jarFile, String jarPath){
        try{
            Enumeration<JarEntry> e = jarFile.entries();
            URL[] urls = { new URL("jar:file:" + jarPath+"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                // -6 because of .class
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                Class<?> c = cl.loadClass(className);
                ComponentsCtrl.getDataCtrl().getDataFlow().addClassObject(c);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    /*
     *This method delete all .java file collected
     */
    private void deleteJavaFiles(){
        ComponentsCtrl.getDataCtrl().getDataFlow().deleteJavaFiles();
    }




}
