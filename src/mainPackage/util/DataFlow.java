package mainPackage.util;

import mainPackage.data.ExtractorPack;
import mainPackage.data.GeneralsPack;
import mainPackage.logic.*;
import java.io.File;
import java.util.ArrayList;

/**
 * DataFlow is a useful class that contains all the data which has to pass through different
 * scenes of the UI. Once collected it also provide a method to start the metrics extraction, creating a Barrier
 * and launching all the required Threads.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class DataFlow {

    private ArrayList<File> javaFiles;
    private ArrayList<Class<?>> classObjects;
    private Boolean allMetricsBoolean;
    private GeneralsPack generalsPack;
    private ArrayList<ExtractorPack> extractorPacks;

    public DataFlow(){
        this.javaFiles = new ArrayList<File>();
        this.classObjects = new ArrayList<Class<?>>();
        this.allMetricsBoolean = false;
    }

    public ArrayList<File> getJavaFiles() {
        return javaFiles;
    }

    public ArrayList<Class<?>> getClassObjects() {
        return classObjects;
    }

    public void deleteJavaFiles(){
        this.javaFiles = new ArrayList<File>();
    }

    public void addJavaFile(File f){
        javaFiles.add(f);
    }

    public void addClassObject(Class<?> c){
        classObjects.add(c);
    }


    /**
     * This method simply create an Instance for the Barrier and invoked the analyze method
     * passing as parameters the javaFiles ArrayList, the classObjects ArrayList and the Barrier in order to calculate
     * all the metrics.
     */
    public void calculateAllMetrics(){
        System.err.println("ANALYZING " + javaFiles.size() + " FILES.");
        Barrier barrier = new Barrier(javaFiles.size(), new AllObserver());
        analyze(javaFiles, classObjects, barrier);
    }

    /**
     * This method simply create an Instance for the Barrier and invoked the analyze method
     * passing as parameters only the javaFiles ArrayList and the Barrier in order to calculate only code metrics.
     */
    public void calculateCodeMetrics(){
        System.err.println("ANALYZING " + javaFiles.size() + " FILES.");
        Barrier barrier = new Barrier(javaFiles.size(), new CodeObserver());
        analyze(javaFiles, barrier);
    }

    /**
     * This method matches .java files and .class files for name.
     * For every pair found it launches an MetricsExtractor Thread
     * @param javaFiles
     * @param classes
     * @param barrier
     */
    private void analyze(ArrayList<File> javaFiles, ArrayList<Class<?>> classes, Barrier barrier){
        try{
            for(File f : javaFiles){
                for(Class<?> c: classes){
                    if(f.getPath().endsWith(c.getSimpleName() + ".java")){
                        new AllExtractor(f, c, barrier).start();
                        break;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
}

    /**
     * This method launch an MetricsExtractor Thread for for every .java file in the javaFiles ArrayList.
     * @param javaFiles
     * @param barrier
     */
    private void analyze(ArrayList<File> javaFiles, Barrier barrier){
        try{
            for(File f : javaFiles){
                new CodeExtractor(f, barrier).start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setAllMetricsBoolean(){
        if(allMetricsBoolean == false){
            allMetricsBoolean = true;
        }
    }

    public Boolean getAllMetricsBoolean() {
        return allMetricsBoolean;
    }

    public GeneralsPack getGeneralsPack() {
        return generalsPack;
    }

    public ArrayList<ExtractorPack> getExtractorPacks() {
        return extractorPacks;
    }

    public void setGeneralsPack(GeneralsPack generalsPack) {
        this.generalsPack = generalsPack;
        this.extractorPacks = this.generalsPack.getDataPacks();
    }

}
