package mainPackage.data;

import java.util.ArrayList;

/**
 * This abstract class contains all data in common between a code analysis with and
 * without the compiled code.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public abstract class GeneralsPack {

    protected int totLOC;
    protected int totCommentLines;
    protected int totBlankLines;
    protected int totNOM;
    protected int totNPM;
    protected ArrayList<ExtractorPack> dataPacks;

    public abstract void computeMaxDIT();

    public abstract void computeNOC();

    public abstract void computeCBO();

    public void printResults(){
        System.out.println(this.toString());
        printSpecificResults();
    }

    protected void printSpecificResults(){
        for(ExtractorPack p : this.dataPacks){
            System.out.println(p.toString());
            p.printFunctionMetrics();
        }
    }

    public void computeGeneralVaulues(){
        for(ExtractorPack p : this.dataPacks){
            totLOC += p.getLOC();
            totCommentLines += p.getCommentLines();
            totBlankLines += p.getBlankLines();
            totNOM += p.getNOM();
            totNPM += p.getNPM();
        }
    }


    public void computeMOA(){
        ArrayList<String> userTypes = new ArrayList<String>();
        for(ExtractorPack p : this.dataPacks){
            userTypes.add(p.getFileName());
        }
        for(ExtractorPack p : this.dataPacks){
            ArrayList<String> fieldsType = p.getFieldsType();
            for(String s : fieldsType){
                for(String userType: userTypes){
                    if(s.contains(userType)){
                        p.countMOA(p.getFields().get(s).size());
                    }
                }
            }
        }
    }

    public ArrayList<ExtractorPack> getDataPacks() {
        return dataPacks;
    }

    public int getTotLOC() {
        return totLOC;
    }

    public int getTotCommentLines() {
        return totCommentLines;
    }

    public int getTotBlankLines() {
        return totBlankLines;
    }

    public int getTotNOM() {
        return totNOM;
    }

    public int getTotNPM() {
        return totNPM;
    }

    public abstract String toCsvString(String separator);
}
