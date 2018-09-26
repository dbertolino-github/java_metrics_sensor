package mainPackage.logic;

import mainPackage.ComponentsCtrl;
import mainPackage.data.CodeExtractorPack;
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
public class CodeExtractor extends MetricsExtractor {

    /**
     * Second constructor, without a class object to represent the compiled code.
     * @param f (.java file to analyse)
     * @param b (Barrier for the Thread)
     */
    public CodeExtractor(File f, Barrier b) {
        try{
            this.barrier = b;
            this.file = f;
            this.is = new FileInputStream(f.getAbsolutePath());
            this.input = new ANTLRInputStream(is);
            this.lexer = new Java8Lexer(input);
            this.tokens = new CommonTokenStream(lexer);
            this.parser = new Java8Parser(tokens);
            this.listener = new FileListener(parser);
            this.tree = parser.compilationUnit();
            this.walker = new ParseTreeWalker();
            this.walker.walk(listener, tree);
            this.fileListenerPack = listener.getFileListenerPack();
            this.extractorPack = new CodeExtractorPack(fileListenerPack);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void run(){
        try {
            countCommentLines(file);
            countWhiteLines(file);
            extract(file);
            this.barrier.addExtactorPack(extractorPack);
            ComponentsCtrl.progress();
            this.barrier.waitB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method get the FileListenerPack of data and passes them into the CodeExtractorPack of the Thread.
     * @param importFile
     * @throws IOException
     */
    private void extract(File importFile) {
        extractorPack.computeLOCandAMC();
        extractorPack.computeRFC();
        extractorPack.computeWMC();
        extractorPack.computeAvgCC();
        extractorPack.computeDAM();
        extractorPack.computeCAM();
        extractorPack.computeLCOM3();
    }
}
