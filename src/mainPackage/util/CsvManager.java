package mainPackage.util;

import mainPackage.data.ExtractorPack;
import mainPackage.data.FunctionListenerPack;
import mainPackage.data.GeneralsPack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class CsvManager {

    private final String DEFAULT_SEPARATOR = "_";
    private BufferedWriter totWriter;
    private BufferedWriter classWriter;
    private BufferedWriter functionWriter;

    public void createFiles(File fileObject, String fileName ){
        String totFileDir = fileObject.getAbsolutePath() + "/" + fileName + "Tot.csv";
        String classFileDir = fileObject.getAbsolutePath() + "/" + fileName + "Class.csv";
        String functionFileDir = fileObject.getAbsolutePath() + "/" + fileName + "Func.csv";
        File totFile = new File(totFileDir);
        File classFile = new File(classFileDir);
        File functionFile = new File(functionFileDir);

        try{
            totWriter = new BufferedWriter(new FileWriter(totFile));
            classWriter = new BufferedWriter(new FileWriter(classFile));
            functionWriter = new BufferedWriter(new FileWriter(functionFile));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTotValues(GeneralsPack generalsPack) {
        try {
            totWriter.write("LOC" + DEFAULT_SEPARATOR +
                    "Comments" + DEFAULT_SEPARATOR +
                    "Blank" + DEFAULT_SEPARATOR +
                    "NOM" + DEFAULT_SEPARATOR +
                    "NPM");
            totWriter.newLine();
            totWriter.write(generalsPack.toCsvString(DEFAULT_SEPARATOR));
            totWriter.newLine();
            totWriter.flush();
            totWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTotValuesAll(GeneralsPack generalsPack) {
        try {
            totWriter.write("LOC" + DEFAULT_SEPARATOR +
                    "Comments" + DEFAULT_SEPARATOR +
                    "Blank" + DEFAULT_SEPARATOR +
                    "NOM" + DEFAULT_SEPARATOR +
                    "NPM" + DEFAULT_SEPARATOR +
                    "DIT");
            totWriter.flush();
            totWriter.newLine();
            totWriter.write(generalsPack.toCsvString(DEFAULT_SEPARATOR));
            totWriter.flush();
            totWriter.newLine();
            totWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeClassValues(GeneralsPack generalsPack) {
        try{
            classWriter.write("Name" + DEFAULT_SEPARATOR +
                    "Type" + DEFAULT_SEPARATOR +
                    "Comments" + DEFAULT_SEPARATOR +
                    "Blank" + DEFAULT_SEPARATOR +
                    "LOC" + DEFAULT_SEPARATOR +
                    "NOM" + DEFAULT_SEPARATOR +
                    "NPM" + DEFAULT_SEPARATOR +
                    "WMC" + DEFAULT_SEPARATOR +
                    "CC" + DEFAULT_SEPARATOR +
                    "AMC" + DEFAULT_SEPARATOR +
                    "DAM" + DEFAULT_SEPARATOR +
                    "MOA" + DEFAULT_SEPARATOR +
                    "RFC" + DEFAULT_SEPARATOR +
                    "CAM" + DEFAULT_SEPARATOR +
                    "LCOM3" );
            classWriter.flush();
            classWriter.newLine();

            for(ExtractorPack e : generalsPack.getDataPacks()){
                classWriter.write((e.toCsvString(DEFAULT_SEPARATOR)));
                classWriter.flush();
                classWriter.newLine();
            }
            classWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeClassValuesAll(GeneralsPack generalsPack) {
        try{
            classWriter.write( "Name" + DEFAULT_SEPARATOR +
                    "Type" + DEFAULT_SEPARATOR +
                    "Comments" + DEFAULT_SEPARATOR +
                    "Blank" + DEFAULT_SEPARATOR +
                    "LOC" + DEFAULT_SEPARATOR +
                    "NOM" + DEFAULT_SEPARATOR +
                    "NPM" + DEFAULT_SEPARATOR +
                    "WMC" + DEFAULT_SEPARATOR +
                    "CC" + DEFAULT_SEPARATOR +
                    "AMC" + DEFAULT_SEPARATOR +
                    "DAM" + DEFAULT_SEPARATOR +
                    "MOA" + DEFAULT_SEPARATOR +
                    "RFC" + DEFAULT_SEPARATOR +
                    "CAM" + DEFAULT_SEPARATOR +
                    "LCOM3" + DEFAULT_SEPARATOR +
                    "DIT" + DEFAULT_SEPARATOR +
                    "NOC" + DEFAULT_SEPARATOR +
                    "CBO");
            classWriter.flush();
            classWriter.newLine();

            for(ExtractorPack e : generalsPack.getDataPacks()){
                classWriter.write((e.toCsvString(DEFAULT_SEPARATOR)));
                classWriter.flush();
                classWriter.newLine();
            }
            classWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeFunctionValues(GeneralsPack generalsPack) {
        try{
            functionWriter.write("ClassName" + DEFAULT_SEPARATOR +
                    "FunctionName" + DEFAULT_SEPARATOR +
                    "LOC" + DEFAULT_SEPARATOR +
                    "CC");
            functionWriter.flush();
            functionWriter.newLine();

            for(ExtractorPack e : generalsPack.getDataPacks()){
                for(FunctionListenerPack f : e.getFunctionPacks()){
                    functionWriter.write(f.toCsvString(DEFAULT_SEPARATOR));
                    functionWriter.flush();
                    functionWriter.newLine();
                }
            }
            functionWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
