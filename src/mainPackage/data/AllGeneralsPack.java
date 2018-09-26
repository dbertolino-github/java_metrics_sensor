package mainPackage.data;

import java.util.ArrayList;

/**
 * This class contains all data collected by the AllObserver thread
 * with the compiled code available.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class AllGeneralsPack extends GeneralsPack{

    private Integer maxDIT;

    public AllGeneralsPack(ArrayList<ExtractorPack> dP){
        this.totLOC = 0;
        this.totCommentLines = 0;
        this.totBlankLines = 0;
        this.totNOM = 0;
        this.totNPM = 0;
        this.maxDIT = 0;
        this.dataPacks = dP;
    }

    @Override
    public String toString(){
        return "\nTOTAL LOC: " + this.totLOC +
                "\nTOT Comments: " + this.totCommentLines +
                "\nTOT blank lines: " + this.totBlankLines +
                "\nTOTAL NOM: " + this.totNOM +
                "\nTOTAL NPM: " + this.totNPM +
                "\nMAX DIT: " + this.maxDIT +
                "\n";
    }

    /**
     * This method create a String to be written in the csv result
     * @param separator of the csv file
     * @return String
     */
    @Override
    public String toCsvString(String separator) {
        return totLOC + separator + totCommentLines + separator + totBlankLines + separator + totNOM + separator + totNPM + separator + maxDIT;
    }

    public Integer getMaxDIT() {
        return maxDIT;
    }

    /**
     * This method computes the max value for DIT between all analysed classes.
     */
    @Override
    public void computeMaxDIT() {
        for (ExtractorPack pack : this.dataPacks) {
            AllExtractorPack p = (AllExtractorPack) pack;
            int d = p.getDIT();
            if (this.maxDIT < d) {
                this.maxDIT = d;
            }
        }
    }

    /**
     * This method computes NOC value for all classes.
     */
    @Override
    public void computeNOC() {
        for (ExtractorPack ep : this.dataPacks) {
            AllExtractorPack examinedPack = (AllExtractorPack) ep;
            for (ExtractorPack p : this.dataPacks) {
                AllExtractorPack pack = (AllExtractorPack) p;
                if(examinedPack.getSuperClassName() != null){
                    if (examinedPack.getSuperClassName().equals(pack.getFileName())) {
                        pack.increaseNOC();
                    }
                }
            }
        }
    }

    /**
     * This method invokes computeAfferentCoupledTypes and calculateCBO methods.
     */
    @Override
    public void computeCBO(){
        computeAfferentCoupledTypes(extractClassAndInterfaceNames());
        calculateCBO(extractClassAndInterfaceNames());
    }

    /**
     * This method extracts from the DataPacks all names of the files analysed.
     * @return ArrayList containing all class/interface names
     */
    private ArrayList<String> extractClassAndInterfaceNames(){
        ArrayList<String> names = new ArrayList<String>();
        for(ExtractorPack p : this.dataPacks){
            names.add(p.getFileName());
        }
        return names;
    }

    /**
     * This method computes all afferent coupled types using the efferent coupled types
     * computed before.
     * @param names
     */
    private void computeAfferentCoupledTypes(ArrayList<String> names){
        for(ExtractorPack pack : this.dataPacks){
            AllExtractorPack p = (AllExtractorPack)pack;
            String afferentType = p.getFileName();
            for(String type : p.getEfferentCoupledTypes()){
                for(String name : names){
                    if(type.equals(name) || type.contains(name)){
                        addAfferentType(name, afferentType);
                    }
                }
            }
        }
    }

    /**
     * This method add an afferent type to a DataPack.
     * @param fileName class/interface to which the type is afferent coupled
     * @param afferentType Type to be added
     */
    private void addAfferentType(String fileName, String afferentType ){
        for(ExtractorPack pack : this.dataPacks){
            AllExtractorPack p = (AllExtractorPack)pack;
            if(fileName.equals(p.getFileName())){
                p.addAfferentType(afferentType);
            }
        }
    }

    /**
     * This method calculates CBO value for all DataPacks.
     * @param names
     */
    private void calculateCBO(ArrayList<String> names){
        for(ExtractorPack pack : this.dataPacks){
            AllExtractorPack p = (AllExtractorPack)pack;
            ArrayList<String> couplingTypes = new ArrayList<String>();
            for(String eff : p.getEfferentCoupledTypes()){
                if(names.contains(eff)){
                    if(!(couplingTypes.contains(eff))){
                        couplingTypes.add(eff);
                    }
                }
            }
            for(String aff : p.getAfferentCoupledTypes()){
                if(names.contains(aff)){
                    if(!(couplingTypes.contains(aff))){
                        couplingTypes.add(aff);
                    }
                }
            }
            p.setCBO(couplingTypes.size());
        }
    }

}
