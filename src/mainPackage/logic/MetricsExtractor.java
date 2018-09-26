package mainPackage.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import mainPackage.data.ExtractorPack;
import mainPackage.data.FileListenerPack;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import mainPackage.generated.Java8Lexer;
import mainPackage.generated.Java8Parser;

/**
 * MetricsExtractor class implements a Thread to be launched for every file .java found in source code to analyse.
 * The class has two different constructors, one take as a parameter also a Class object which has to represent the compiled code
 * of the .java file analysed.
 * Its scope is to "extract" useful data from the code that will be successively used by the MetricsObserver Thread.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public abstract class MetricsExtractor extends Thread {
	
	protected InputStream is;
	protected ANTLRInputStream input;
	protected Java8Lexer lexer;
	protected CommonTokenStream tokens;
	protected Java8Parser parser;
	protected FileListener listener;
	protected ParseTree tree;
	protected ParseTreeWalker walker;
	protected BufferedReader reader;
	protected File file;
	protected Barrier barrier;
	protected FileListenerPack fileListenerPack;
	protected ExtractorPack extractorPack;


	/**
	 * This method read the .java file line by line searching for comment and white lines, counting them.
	 * @param importFile
	 * @throws IOException 
	 */
	protected void countCommentLines(File importFile){

		try{
			reader = new BufferedReader(new FileReader(importFile));
			String line;
			while ((line = reader.readLine()) != null){
				if(line.trim().startsWith("//")){
					extractorPack.countCommentLines();
				}
				else if(line.trim().startsWith("/*")){
					extractorPack.countCommentLines();
					while(!(line.trim().endsWith("*/"))){
						line = reader.readLine();
						extractorPack.countCommentLines();
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}


	}

	protected void countWhiteLines(File importFile){
		try{
			reader = new BufferedReader(new FileReader(importFile));
			String line;
			while ((line = reader.readLine()) != null){
				if(line.trim().isEmpty()){
					extractorPack.countBlankLines();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
