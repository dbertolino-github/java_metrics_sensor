package mainPackage.data;

import java.util.ArrayList;

/**
 * This abstract class contains all data in common between a code analysis with and
 * without the compiled code.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public abstract class ExtractorPack extends DataPack {

    protected int blankLines;
    protected int commentLines;
    protected int WMC;
    protected int MOA;
    protected int RFC;
    protected float CAM;
    protected float DAM;
    protected float avgCC;
    protected int AMC;
    protected float LCOM3;

    /**
     * This method create a String to be written in the csv result
     * @param separator of the csv file
     * @return String
     */
    public abstract String toCsvString(String separator);

    public abstract void computeDIT();

    public abstract void computeEfferentCoupledTypes();

    public abstract Class<?> getClassObject();

    public int getBlankLines() {
        return blankLines;
    }

    public void countBlankLines() {
        this.blankLines++;
    }

    public int getCommentLines() {
        return commentLines;
    }

    public void countCommentLines() {
        this.commentLines++;
    }


    public void countMOA(int x){
        this.MOA = this.MOA + x;
    }

    public void computeLOCandAMC(){
        for(FunctionListenerPack p : this.functionsMetrics){
            this.LOC += p.getLOC();
            this.AMC += p.getLOC();
        }
    }

    public void computeWMC(){
        for(FunctionListenerPack p : this.getFunctionPacks()){
            this.WMC += p.getCC();
        }
    }

    public void computeAvgCC() {
        float sum = 0;
        for(FunctionListenerPack p : this.getFunctionPacks()){
            sum += (float)p.getCC();
        }
        avgCC = sum / (float)this.getFunctionPacks().size();
    }

    public void computeDAM() {
        if(this.NOF != 0){
            this.DAM = (this.NPPF != 0)? (float) this.NPPF / (float) this.NOF : 0;
        } else {
            this.DAM = 1;
        }
    }

    public void computeRFC(){
        this.RFC += this.NOM;
        for(FunctionListenerPack p : this.getFunctionPacks()){
            this.RFC += p.getNumberOfMethodsInvoked();
        }
    }

    public void computeCAM(){
        ArrayList<String> differentClassParameterTypes = new ArrayList<String>();
        int sumDifferentTypesPerFunction = 0;
        int numClassDifferentTypes = 0;
        for(FunctionListenerPack pack : this.functionsMetrics){
            ArrayList<String> differentFunctionParameterTypes = new ArrayList<String>();
            for(String type : pack.getTypeParametersList()){
                if(!differentFunctionParameterTypes.contains(type)){
                    differentFunctionParameterTypes.add(type);
                }
                if(!differentClassParameterTypes.contains(type)){
                    differentClassParameterTypes.add(type);
                }
            }
            sumDifferentTypesPerFunction += differentFunctionParameterTypes.size();
        }
        numClassDifferentTypes = differentClassParameterTypes.size();
        if(sumDifferentTypesPerFunction == 0){
            this.CAM = 1;
        }else{
            this.CAM = ((float)sumDifferentTypesPerFunction /(float) (numClassDifferentTypes*this.NOM)) ;
        }

    }

    public void computeLCOM3(){
        int numFunctions = this.NOM;
        int numFields = this.NOF;
        int sumOfNumberOfMethodsThatAccessPerFields = 0;
        if((numFields == 0) || (numFunctions <= 1)){
            this.LCOM3 = 0;
        }else{
            for(String fieldName : this.getFieldsName()){
                for(FunctionListenerPack pack : this.functionsMetrics){
                    if(pack.getAccededFields().contains(fieldName)){
                        sumOfNumberOfMethodsThatAccessPerFields++;
                    }
                }
            }
            this.LCOM3 = ((((float)1/numFields)*sumOfNumberOfMethodsThatAccessPerFields)-numFunctions)/(1-numFunctions);
        }

    }

    public void printFunctionMetrics(){
        ArrayList<FunctionListenerPack> functions = this.functionsMetrics;
        for(FunctionListenerPack m : functions){
            System.out.println(m.toString());
        }
        System.out.println("\n");
    }

}
