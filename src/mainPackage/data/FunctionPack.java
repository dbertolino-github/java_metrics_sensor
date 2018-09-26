package mainPackage.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class FunctionPack {

    private String className;
    private int CC;
    private int LOC;
    private int numParam;
    private int numMethodsInvoked;
    private String functionName;
    private ArrayList<String> typeParametersList;
    private ArrayList<String> accededFields;
    private ArrayList<String> coupledVariables;
    private HashMap<String,ArrayList<String>> variablesType;

    public FunctionPack(String name){
        this.CC = 1;
        this.LOC = 0;
        this.numParam = 0;
        this.functionName = name;
        this.typeParametersList = new ArrayList<String>();
        this.accededFields = new ArrayList<String>();
        this.coupledVariables = new ArrayList<String>();
        this.variablesType = new HashMap<String, ArrayList<String>>();
        this.numMethodsInvoked = 0;
    }

    public void addDecisionalPoints(int i){
        this.CC += i;
    }

    public int getCC() { return this.CC; }

    public void countLoc(){
        this.LOC++;
    }

    public int getLOC() {
        return this.LOC;
    }

    public void setClassName(String n){
        this.className = n;
    }

    public void countParam(){
        this.numParam++;
    }

    public void addParamType(String type){
        this.typeParametersList.add(type);
    }

    public ArrayList<String> getTypeParametersList() { return this.typeParametersList;}

    public int getNumberOfMethodsInvoked(){
        return this.numMethodsInvoked;
    }

    public void countMethodsInvoked(){
        this.numMethodsInvoked++;
    }

    public ArrayList<String> getAccededFields(){
        return this.accededFields;
    }

    public void addAccededField(String f) {
        this.accededFields.add(f);
    }

    public void addCoupledVariable(String variable){
        if(!(this.coupledVariables.contains(variable))){
            this.coupledVariables.add(variable);
        }
    }

    public ArrayList<String> getCoupledVariables(){
        return this.coupledVariables;
    }

    public void addTypeVariables(String key, ArrayList<String> names){
        if(variablesType.containsKey(key)){
            for(String n : names){
                variablesType.get(key).add(n);
            }
        }else{
            this.variablesType.put(key, names);
        }
    }

    public HashMap<String, ArrayList<String>> getTypeVariables(){
        return this.variablesType;
    }


    public String toString(){
        String param = "(";
        if(numParam == 0){
            param = param + ")";
        }else{
            for(String type : this.typeParametersList){
                param = param + type + ",";
            }
            param = param.substring(0, param.length()-1) + ")";;
        }
        return "{" + this.functionName + param +
                ", LOC: " + this.LOC +
                ", CC: " + this.CC + "}";
    }

}
