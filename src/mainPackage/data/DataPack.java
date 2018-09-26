package mainPackage.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DataPack is an abstract class which defines common data between FileListener class
 * and ExtractorPack class.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public abstract class DataPack {

    protected HashMap<String,ArrayList<String>> fields;
    protected String fileName;
    protected String fileType;
    protected int NOM;
    protected int NPM;
    protected int NOF;
    protected int NPPF;
    protected int LOC;
    protected ArrayList<FunctionListenerPack> functionsMetrics;

    public HashMap<String,ArrayList<String>> getFields() {
        return this.fields;
    }

    /**
     * This method extracts the key set of the HashMap fields.
     * @return ArrayList containing the types of all fields of the class/interface.
     */
    public ArrayList<String> getFieldsType() {
        ArrayList<String> fieldsType = new ArrayList<String>();
        for (String type : this.fields.keySet()) {
            fieldsType.add(type);
        }
        return fieldsType;
    }

    /**
     * This methods return all fields names of the class/interface.
     * @return ArrayList containing the names of all fields of the class/interface.
     */
    public ArrayList<String> getFieldsName(){
        ArrayList<String> fieldsName = new ArrayList<String>();
        for(String type : this.fields.keySet()){
            fieldsName.addAll(this.fields.get(type));
        }
        return fieldsName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String n) {
        this.fileName = n;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String t) {
        this.fileType = t;
    }

    public int getNOM() {
        return NOM;
    }

    public void countNOM() {
        this.NOM++;
    }

    public int getNPM() {
        return NPM;
    }

    public void countNPM() {
        this.NPM++;
    }

    public int getLOC() {
        return LOC;
    }

    public void countLOC() {
        this.LOC++;
    }

    public void addLOC(int loc) {
        this.LOC += loc;
    }

    public void addFunctionPack(FunctionListenerPack pack){
        functionsMetrics.add(pack);
    }

    public ArrayList<FunctionListenerPack> getFunctionPacks(){
        return this.functionsMetrics;
    }




}
