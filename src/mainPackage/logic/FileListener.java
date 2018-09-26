package mainPackage.logic;

import mainPackage.data.FileListenerPack;
import mainPackage.data.FunctionListenerPack;
import mainPackage.generated.Java8BaseListener;
import mainPackage.generated.Java8Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

/**
 * FileListener extends Java8BaseListener class generated from Java8 grammar with ANTLR4.
 * His scope is to listen to the default walker while visiting the AST of the relative .java file.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class FileListener extends Java8BaseListener {

	private Java8Parser parser;
	private FunctionListener functionListener;
	private FileListenerPack fileListenerPack;

	public FileListener(Java8Parser p) {
		this.parser = p;
		this.fileListenerPack = new FileListenerPack();
	}

	public FileListenerPack getFileListenerPack(){
		return fileListenerPack;
	}

	@Override
	public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
		String name = ctx.methodHeader().methodDeclarator().Identifier().getText();
		List<Java8Parser.MethodModifierContext> modifiers = ctx.methodModifier();
		for(Java8Parser.MethodModifierContext c : modifiers){
			if(c.getText().contains("public")){
				fileListenerPack.countNPM();
				break;
			}
		}
		fileListenerPack.countNOM();
		fileListenerPack.countLOC();
		List<ParseTree> children = ctx.children;
		FunctionListenerPack p = new FunctionListenerPack(name);
		functionListener = new FunctionListener(p , this.parser);
		for(ParseTree tree : children){
			ParseTreeWalker walker = new ParseTreeWalker();
			walker.walk(functionListener, tree);
		}
		fileListenerPack.addFunctionPack(p);

	}

	@Override
	public void enterConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx){
		String name = ctx.constructorDeclarator().simpleTypeName().Identifier().getText();
		List<Java8Parser.ConstructorModifierContext> modifiers = ctx.constructorModifier();
		for(Java8Parser.ConstructorModifierContext c : modifiers){
			if(c.getText().contains("public")){
				fileListenerPack.countNPM();
				break;
			}
		}
		fileListenerPack.countNOM();
		fileListenerPack.countLOC();
		List<ParseTree> children = ctx.children;
		FunctionListenerPack p = new FunctionListenerPack(name);
		functionListener = new FunctionListener(p , this.parser);
		for(ParseTree tree : children){
			ParseTreeWalker walker = new ParseTreeWalker();
			walker.walk(functionListener, tree);
		}
		fileListenerPack.addFunctionPack(p);
	}
	
	@Override 
	public void enterNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
		fileListenerPack.setFileName(ctx.Identifier().toString());
		fileListenerPack.setFileType("Class");
		
	}
	
	@Override 
	public void enterNormalInterfaceDeclaration(Java8Parser.NormalInterfaceDeclarationContext ctx) { 
		fileListenerPack.setFileName(ctx.Identifier().toString());
		fileListenerPack.setFileType("Interface");
	}

	@Override
	public void enterFieldDeclaration(Java8Parser.FieldDeclarationContext ctx){
		int numDeclared = ctx.variableDeclaratorList().variableDeclarator().size();
		fileListenerPack.addLOC(numDeclared);
		fileListenerPack.addNOF(numDeclared);
		List<Java8Parser.FieldModifierContext> modifiers = ctx.fieldModifier();
		for(Java8Parser.FieldModifierContext c : modifiers){
			if(c.getText().contains("private")){
				fileListenerPack.addNPPF(numDeclared);
				break;
			}
			else if(c.getText().contains("protected")){
				fileListenerPack.addNPPF(numDeclared);
				break;
			}
		}

		String type = ctx.unannType().getText();
		ArrayList<String> names = new ArrayList<String>();
		for(Java8Parser.VariableDeclaratorContext c : ctx.variableDeclaratorList().variableDeclarator()){
			names.add(c.variableDeclaratorId().getText());
		}
		fileListenerPack.addFields(type, names);
	}

}
