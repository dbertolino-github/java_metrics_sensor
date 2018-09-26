package mainPackage.logic;

import mainPackage.ComponentsCtrl;
import mainPackage.data.AllExtractorPack;
import mainPackage.generated.Java8Lexer;
import mainPackage.generated.Java8Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class AllExtractor extends MetricsExtractor {

    /**
     * First constructor.
     * @param f (.java file to analyse)
     * @param c (class object for the complied class)
     * @param b (Barrier for the Thread)
     */
    public AllExtractor(File f, Class<?> c, Barrier b) {
        this.barrier = b;
        this.file = f;
        try{
            this.is = new FileInputStream(f.getAbsolutePath());
            this.input = new ANTLRInputStream(is);
        }catch(IOException e){e.printStackTrace();}
        this.lexer = new Java8Lexer(input);
        this.tokens = new CommonTokenStream(lexer);
        this.parser = new Java8Parser(tokens);
        this.listener = new FileListener(parser);
        this.tree = parser.compilationUnit();
        this.walker = new ParseTreeWalker();
        this.walker.walk(listener, tree);
        this.fileListenerPack = listener.getFileListenerPack();
        this.extractorPack = new AllExtractorPack(fileListenerPack, c);
    }

    public void run(){
        try {
            countCommentLines(file);
            countWhiteLines(file);
            extract(file, this.extractorPack.getClassObject());
            this.barrier.addExtactorPack(extractorPack);
            ComponentsCtrl.progress();
            this.barrier.waitB();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method get the FileListenerPack of data and passes them into the CodeExtractorPack of the Thread.
     * It also calculate some structure metrics using the Class object
     * @param importFile
     * @param classObject
     * @throws IOException
     */
    private void extract(File importFile, Class<?> classObject){
        extractorPack.computeLOCandAMC();
        extractorPack.computeRFC();
        extractorPack.computeWMC();
        extractorPack.computeAvgCC();
        extractorPack.computeDAM();
        extractorPack.computeCAM();
        extractorPack.computeLCOM3();
        extractorPack.computeDIT();
        extractorPack.computeEfferentCoupledTypes();
    }
}
