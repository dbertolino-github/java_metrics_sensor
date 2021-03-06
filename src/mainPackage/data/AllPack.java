package mainPackage.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class AllPack extends ExtractorPack {

    private String superClassName;
    private int DIT;
    private int NOC;
    private int CBO;
    private Class<?> classObject;
    private ArrayList<String> efferentCoupledTypes;
    private ArrayList<String> afferentCoupledTypes;
    private ArrayList<String> allCoupledVariables;
    private HashMap<String,ArrayList<String>> allVariablesType;

    public AllPack(FileListenerPack fileListenerPack, Class<?> classObject) {
        this.classObject = classObject;
        this.functionsMetrics = fileListenerPack.getFunctionPacks();
        this.fileName = fileListenerPack.getFileName();
        this.fileType = fileListenerPack.getFileType();
        this.LOC = fileListenerPack.getLOC();
        this.NOM = fileListenerPack.getNOM();
        this.NPM = fileListenerPack.getNPM();
        this.NOF = fileListenerPack.getNOF();
        this.NPPF = fileListenerPack.getNPPF();
        this.fields = fileListenerPack.getFields();
        this.allVariablesType = fileListenerPack.getFields();
        this.allCoupledVariables = new ArrayList<String>();
        this.efferentCoupledTypes = new ArrayList<String>();
        this.afferentCoupledTypes = new ArrayList<String>();
        this.commentLines = 0;
        this.blankLines = 0;
        this.DAM = 0;
        this.avgCC = 0;
        this.AMC = 0;
        this.MOA = 0;
        this.DIT = 0;
        this.NOC = 0;
        this.RFC = 0;
        this.CAM = 0;
        this.LCOM3 = 0;
        this.CBO = 0;
        this.superClassName = (this.classObject.getSuperclass() != null)? this.classObject.getSuperclass().getSimpleName() : null;
        for(FunctionListenerPack pack : this.functionsMetrics){
            pack.setClassName(fileName);
        }
    }

    @Override
    public String toString(){
        return this.fileName + " " + this.fileType +
                "\nComment lines: " + this.commentLines +
                "\nBlank lines: " + this.blankLines +
                "\nLOC: " + this.LOC +
                "\nNOM: " + this.NOM +
                "\nNPM: " + this.NPM +
                "\nWMC: " + this.WMC +
                "\nCC: " + this.avgCC +
                "\nAMC: " + this.AMC +
                "\nDAM: " + this.DAM +
                "\nMOA: " + this.MOA +
                "\nRFC: " + this.RFC +
                "\nCAM: " + this.CAM +
                "\nLCOM3: " + this.LCOM3 +
                "\nCBO: " + this.CBO +
                "\nNOC: " + this.NOC +
                "\nDIT: " + this.DIT;
    }

    @Override
    public Class<?> getClassObject() {return this.classObject; }

    @Override
    public String toCsvString(String separator) {
        return "";
    }

    @Override
    public void computeDIT(){
        computeRecursiveDIT(this.classObject);
    }

    private void computeRecursiveDIT(Class<?> c){
        Class<?> superC = c.getSuperclass();
        if(superC != null ){
            if(superC.getName().endsWith("Object")){
                this.DIT = DIT + 1;
            }
            else{
                this.DIT = DIT + 1;
                computeRecursiveDIT(superC);
            }
        }
    }

    @Override
    public void computeEfferentCoupledTypes(){
        extractHalfCoupledTypes();
        unifyCouplingData();
        for(String type : this.allVariablesType.keySet()){
            for(String var : this.allVariablesType.get(type)){
                if(this.allCoupledVariables.contains(var)){
                    if(!(this.efferentCoupledTypes.contains(type))){
                        this.efferentCoupledTypes.add(type);
                    }
                }
            }
        }
    }

    private void extractHalfCoupledTypes(){
        for(Method m : this.classObject.getDeclaredMethods()){

            for(Class<?> type : m.getExceptionTypes()) {
                if (!(this.efferentCoupledTypes.contains(type.getSimpleName()))) {
                    this.efferentCoupledTypes.add(type.getSimpleName());
                }
            }
            Class<?> type = m.getReturnType().getClass();
            if(!(type.getSimpleName().equals("Class")) && !(type.getSimpleName().equals("Object"))){
                if(!(this.efferentCoupledTypes.contains(type.getSimpleName()))){
                    this.efferentCoupledTypes.add(type.getSimpleName());
                }
            }

        }
        if(this.classObject.getSuperclass() != null){
            String superType = this.classObject.getSuperclass().getSimpleName();
            if(!(superType.equals("Class")) && !(superType.equals("Object"))){
                if(!(this.efferentCoupledTypes.contains(superType))){
                    this.efferentCoupledTypes.add(superType);
                }
            }
        }


    }

    private void unifyCouplingData(){
        for(FunctionListenerPack fp: this.getFunctionPacks()){
            for(String var : fp.getCoupledVariables()){
                if(!(this.allCoupledVariables.contains(var))){
                    this.allCoupledVariables.add(var);
                }
            }

            Set<String> types =  fp.getTypeVariables().keySet();
            for(String t : types){
                if(this.allVariablesType.containsKey(t)){
                    for(String t1 : fp.getTypeVariables().get(t)){
                        this.allVariablesType.get(t).add(t1);
                    }
                }else{
                    this.allVariablesType.put(t,fp.getTypeVariables().get(t));
                }
            }
        }
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public int getDIT() {
        return DIT;
    }

    public void increaseNOC() {
        this.NOC = NOC + 1;
    }

    public void addAfferentType(String type){
        if(!(this.afferentCoupledTypes.contains(type))){
            this.afferentCoupledTypes.add(type);
        }
    }

    public void setCBO(int x) {
        this.CBO = x;
    }


    public ArrayList<String> getEfferentCoupledTypes(){
        return this.efferentCoupledTypes;
    }

    public ArrayList<String> getAfferentCoupledTypes(){
        return this.afferentCoupledTypes;
    }



}
