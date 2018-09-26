package mainPackage.logic;

import mainPackage.data.FunctionListenerPack;
import mainPackage.generated.Java8BaseListener;
import mainPackage.generated.Java8Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class FunctionListener extends Java8BaseListener {

    private Java8Parser parser;
    private FunctionListenerPack pack;

    public FunctionListener(FunctionListenerPack pack, Java8Parser p){
        this.parser = p;
        this.pack = pack;
    }

    @Override
    public void enterFormalParameter(Java8Parser.FormalParameterContext ctx){
        pack.countParam();
        pack.addParamType(ctx.unannType().getText() );
        String type = ctx.unannType().getText();
        ArrayList<String> name = new ArrayList<String>();
        name.add(ctx.variableDeclaratorId().getText());
        pack.addParamType(type);
        pack.addTypeVariables(type, name);
    }

    @Override public void enterLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx){
        String type = ctx.unannType().getText();
        List<Java8Parser.VariableDeclaratorContext> list = ctx.variableDeclaratorList().variableDeclarator();
        ArrayList<String> names = new ArrayList<String>();
        for(Java8Parser.VariableDeclaratorContext c : list){
            names.add(c.getText());
        }
        pack.addTypeVariables(type, names);
    }

    @Override
    public void enterLocalVariableDeclarationStatement(Java8Parser.LocalVariableDeclarationStatementContext ctx){
        pack.countLoc();
    }

    @Override
    public void enterReturnStatement(Java8Parser.ReturnStatementContext ctx){
        pack.countLoc();
    }

    @Override
    public void enterExpressionStatement(Java8Parser.ExpressionStatementContext ctx){
        pack.countLoc();
    }

    @Override public void enterThrowStatement(Java8Parser.ThrowStatementContext ctx) {
        pack.countLoc();
    }

    @Override
    public void enterIfThenStatement(Java8Parser.IfThenStatementContext ctx){
        pack.countLoc();
        pack.addDecisionalPoints(1);
    }

    @Override
    public void enterIfThenElseStatement(Java8Parser.IfThenElseStatementContext ctx){
        pack.countLoc();
        pack.addDecisionalPoints(1);
    }

    @Override
    public void enterForStatement(Java8Parser.ForStatementContext ctx) {
        pack.countLoc();
        pack.addDecisionalPoints(1);
    }

    @Override
    public void enterDoStatement(Java8Parser.DoStatementContext ctx) {
        pack.countLoc();
        pack.addDecisionalPoints(1);
    }

    @Override
    public void enterWhileStatement(Java8Parser.WhileStatementContext ctx) {
        pack.countLoc();
        pack.addDecisionalPoints(1);
    }

    @Override
    public void enterSwitchBlock(Java8Parser.SwitchBlockContext ctx) {
        int size = ctx.switchBlockStatementGroup().size();
        List<Java8Parser.SwitchBlockStatementGroupContext> cases = ctx.switchBlockStatementGroup();
        for(Java8Parser.SwitchBlockStatementGroupContext c : cases){
            if(c.switchLabels().getText().contains("default")){
                pack.countLoc();
            }
            else {
                pack.countLoc();
                pack.addDecisionalPoints(1);
            }
        }
    }

    @Override
    public void enterTryStatement(Java8Parser.TryStatementContext ctx) {
        if(ctx.catches() != null){
            int catches = ctx.catches().catchClause().size();
            for(int i = 0; i<catches; i++){pack.countLoc();}
            pack.addDecisionalPoints(catches);
        }
    }

    @Override
    public void enterAssignmentExpression(Java8Parser.AssignmentExpressionContext ctx){
        if(ctx.conditionalExpression()!=null){
            if(ctx.conditionalExpression().getText().contains("?") && ctx.conditionalExpression().getText().contains(":")){
                pack.addDecisionalPoints(1);
            }
        }
    }


    @Override
    public void enterMethodInvocation(Java8Parser.MethodInvocationContext ctx){
        pack.countMethodsInvoked();
        if(ctx.typeName() != null){
            pack.addCoupledVariable(ctx.typeName().Identifier().getText());
        }
        if(ctx.argumentList() != null){
            List<Java8Parser.ExpressionContext> arguments = ctx.argumentList().expression();
            for(Java8Parser.ExpressionContext c : arguments){
                pack.addCoupledVariable(c.getText());
            }
        }
    }


    @Override
    public void enterMethodInvocation_lf_primary(Java8Parser.MethodInvocation_lf_primaryContext ctx){
        pack.countMethodsInvoked();
        if(ctx.argumentList() != null){
            List<Java8Parser.ExpressionContext> arguments = ctx.argumentList().expression();
            for(Java8Parser.ExpressionContext c : arguments){
                pack.addCoupledVariable(c.getText());
            }
        }
    }

    @Override
    public void enterMethodInvocation_lfno_primary(Java8Parser.MethodInvocation_lfno_primaryContext ctx){
        pack.countMethodsInvoked();
        if(ctx.typeName() != null){
            pack.addCoupledVariable(ctx.typeName().Identifier().getText());
        }
        if(ctx.argumentList() != null){
            List<Java8Parser.ExpressionContext> arguments = ctx.argumentList().expression();
            for(Java8Parser.ExpressionContext c : arguments){
                pack.addCoupledVariable(c.getText());
            }
        }
    }

    @Override
    public void enterFieldAccess(Java8Parser.FieldAccessContext ctx){
        pack.addAccededField(ctx.Identifier().getText());
        if(ctx.typeName() != null){
            pack.addCoupledVariable(ctx.typeName().Identifier().getText());
        }
    }

    @Override public void enterFieldAccess_lf_primary(Java8Parser.FieldAccess_lf_primaryContext ctx) {
        pack.addAccededField(ctx.Identifier().getText());
    }

    @Override public void enterFieldAccess_lfno_primary(Java8Parser.FieldAccess_lfno_primaryContext ctx) {
        pack.addAccededField(ctx.Identifier().getText());
        if(ctx.typeName() != null){
            pack.addCoupledVariable(ctx.typeName().Identifier().getText());
        }
    }

}
