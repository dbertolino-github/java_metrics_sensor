package mainPackage.data;

import mainPackage.ComponentsCtrl;

import java.util.ArrayList;

/**
 * This class contains all data collected by the CodeObserver thread
 * without the compiled code available.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class CodeGeneralsPack extends GeneralsPack {

    public CodeGeneralsPack(ArrayList<ExtractorPack> dP){
        this.totLOC = 0;
        this.totCommentLines = 0;
        this.totBlankLines = 0;
        this.totNOM = 0;
        this.totNPM = 0;
        this.dataPacks = dP;
    }

    @Override
    public String toString(){
        return "\nTOTAL LOC: " + this.totLOC +
                "\nTOT Comments: " + this.totCommentLines +
                "\nTOT blank lines: " + this.totBlankLines +
                "\nTOTAL NOM: " + this.totNOM +
                "\nTOTAL NPM: " + this.totNPM +
                "\n";
    }

    /**
     * This method create a String to be written in the csv result
     * @param separator of the csv file
     * @return String
     */
    @Override
    public String toCsvString(String separator) {
        return totLOC + separator + totCommentLines + separator + totBlankLines + separator + totNOM + separator + totNPM;
    }

    /**
     * This method is implemented only in AllObserverPack
     */
    @Override
    public void computeMaxDIT() {}

    /**
     * This method is implemented only in AllObserverPack
     */
    @Override
    public void computeNOC() {}

    /**
     * This method is implemented only in AllObserverPack
     */
    @Override
    public void computeCBO() {}



}
