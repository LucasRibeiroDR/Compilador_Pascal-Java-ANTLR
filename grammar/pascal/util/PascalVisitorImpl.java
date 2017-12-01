/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.util;

import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
import java.util.Scanner;
import pequenopascal.parser.PascalBaseVisitor;
import pequenopascal.parser.PascalParser;

/**
 *
 * @author a120065
 */
public class PascalVisitorImpl extends PascalBaseVisitor<Object> {
    
    
    
    @Override 
    public Object visitWriteSTR(PascalParser.WriteSTRContext ctx) {
        System.out.println(ctx.STR().getText());
        return 0d;
    }
    
    @Override 
    public Object visitWriteEXP(PascalParser.WriteEXPContext ctx) {
        Object result = (Object) visit(ctx.expr());
        System.out.println(result);
        return 0d;
    }
    
    @Override 
    public Object visitExprPlus(PascalParser.ExprPlusContext ctx) {
        Double a = (Double) visit(ctx.expr1());
        Double b = (Double) visit(ctx.expr());
        return a + b;
    }
    
    @Override 
    public Object visitExprMinus(PascalParser.ExprMinusContext ctx) {
        Double a = (Double) visit(ctx.expr1());
        Double b = (Double) visit(ctx.expr());
        return a - b;
    }
    
    @Override 
    public Object visitExpr1Empty(PascalParser.Expr1EmptyContext ctx) {
        return visit(ctx.expr1());
    }
    
    @Override 
    public Object visitExpr1Mult(PascalParser.Expr1MultContext ctx) {
        Double a = (Double) visit(ctx.expr2());
        Double b = (Double) visit(ctx.expr());
        return a * b;
    }
    
    @Override 
    public Object visitExpr1Div(PascalParser.Expr1DivContext ctx) {
        Double a = (Double) visit(ctx.expr2());
        Double b = (Double) visit(ctx.expr());
        return a / b;
    }
    
    @Override 
    public Object visitExpr2Empty(PascalParser.Expr2EmptyContext ctx) {
        return visit(ctx.expr2());
    }
    
    @Override 
    public Object visitExpr2Par(PascalParser.Expr2ParContext ctx) {
        return visit(ctx.expr());
    }
    @Override 
    public Object visitExpr2Num(PascalParser.Expr2NumContext ctx) {
        return Double.parseDouble(ctx.NUM().getText());
    }
    @Override 
    public Object visitV4r(PascalParser.V4rContext ctx) {
        Object[] value = new Object[2];
        value[0] = ctx.types().getText();
        SymbolsTable.getInstance().addSymbol(ctx.ID().get(0).getText(), value);
        return 0d;
    }
    
    @Override 
    public Object visitAttrExpr(PascalParser.AttrExprContext ctx) {
        Object[] lista = SymbolsTable.getInstance().getSymbol(ctx.ID().getText());
        lista[1] = visit(ctx.expr());
        return 0d;
    }
    
    @Override 
    public Object visitReadVar(PascalParser.ReadVarContext ctx) {
        Scanner s = new Scanner(System.in);
        Object[] valor = new Object[2];
        valor[1] = s.nextLine();
        SymbolsTable.getInstance().addSymbol(ctx.ID().getText(), valor);
        return valor;
    }
    
    @Override public Object visitExpr2Var(PascalParser.Expr2VarContext ctx) {
        return SymbolsTable.getInstance().getSymbol(ctx.VAR().getText());
        
    }
    
    @Override 
    public Object visitCondRelOp(PascalParser.CondRelOpContext ctx) {
        Double a = (Double) visit(ctx.expr(0));
        Double b = (Double) visit(ctx.expr(1));
        
        int op = ctx.relop.getType(); ///
        
        if(op == PascalParser.EQ){
            return a=b;
        }
        if(op < PascalParser.LT){
            return a<b;
        }
        if(op > PascalParser.GT){
            return a>b;
        }
        if(op <= PascalParser.LE){
            return a<=b;
        }
        if(op >= PascalParser.GE){
            return a>=b;
        }
        
        return 0d;
    }
    
    @Override 
    public Object visitIfStmt(PascalParser.IfStmtContext ctx) {
        Boolean mirage = (Boolean) visit(ctx.condExpr());
        if(mirage){
            return visit(ctx.b1);
        }
        return 0d;
        //PARAMOS AQUI NESSA CAPETA
    }
}
