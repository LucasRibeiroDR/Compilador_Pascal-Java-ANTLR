/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.util;


import java.util.Scanner;
import pequenopascal.parser.PascalBaseVisitor;
import pequenopascal.parser.PascalParser;
//import pequenopascal.parser.PascalLexer
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 *
 * @author a120065
 */
public class PascalVisitorImpl extends PascalBaseVisitor<Object> {
    
    
    
    @Override 
    public Object visitWriteSTR(PascalParser.WriteSTRContext ctx) {
        String v = ctx.STR().getText();
        char aspas = '\0';
        v = v.replace('"', aspas);
        System.out.println(v);
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
        value[0] = ctx.simpletype().getText();
        SymbolsTable.getInstance().addSymbol(ctx.ID().get(0).getText(), value);
        System.out.println("Criando " + ctx.ID().get(0).getText());
        return 0d;
    }
    
    @Override 
    public Object visitAttrExpr(PascalParser.AttrExprContext ctx) {
       if(SymbolsTable.getInstance().getSymbol(ctx.varDeclapart().getText()) != null){Object aux = visit(ctx.expr());
        if(aux == null){aux = ctx.varDeclapart().getText();} 
        Object[] var = SymbolsTable.getInstance().getSymbol(ctx.varDeclapart().getText());
            if(var[0].equals("float")){
                var[1] = Float.parseFloat(aux.toString());
            }else if(var[0].equals("integer")){           
                var[1] = (int)Double.parseDouble(aux.toString());
            }
            else if(var[0].equals("boolean")){
                if(aux.toString().equals("true") || aux.toString().equals("false")){
                    var[1] = aux;
                }else{
                    throw new ParseCancellationException("Espera um bool");
                }
            }else if(var[0].equals("string")){
                if(aux.toString().startsWith("\"")){
                    var[1] = aux;
                }else{
                    throw new ParseCancellationException("Espera uma string");
                }
            }
        }else{
            throw new ParseCancellationException("Variavel Não existe");
        }         
    return 0;
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
        Object[] var = new Object[2];
        System.out.println("Buscando" + ctx.ID().getText());
        var[1] = SymbolsTable.getInstance().getSymbol(ctx.ID().getText());
        
        return var[1];
        
    }
    
    @Override 
    public Object visitCondRelOp(PascalParser.CondRelOpContext ctx) {
//       Object c = (Object) visit(ctx.expr(0));
//        Object d = (Object) visit(ctx.expr(1))
//        Double a = Double.parseDouble(c.toString());
//        Double b = Double.parseDouble(d.toString());
        
           Double a = (Double) visit(ctx.expr(0));
           Double b = (Double) visit(ctx.expr(1));


        int op = ctx.relop.getType(); ///
        
        if(op == PascalParser.EQ){
            return a = b;
        }
        if (op == PascalParser.NE){
            return a != b;
        }
        if(op < PascalParser.LT){
            return a < b;
        }
        if(op > PascalParser.GT){
            return a > b;
        }
        if(op <= PascalParser.LE){
            return a <=  b;
        }
        if(op >= PascalParser.GE){
            return a >= b;
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
        
     
    }
    @Override 
    public Object visitWhili1(PascalParser.Whili1Context ctx) {
        Boolean issae = (Boolean) visit(ctx.condExpr());
        while(issae){
            visit(ctx.x);
        
        }
        return 0d;
    }
    
    @Override 
    public Object visitIfElseStmt(PascalParser.IfElseStmtContext ctx) {
         Boolean cond = (Boolean) visit(ctx.condExpr());
        
        if (cond) {
            return visit(ctx.b1);
        } else {
            return visit(ctx.b2);
        }
        
    }
    
    @Override 
    public Object visitCondExpresion(PascalParser.CondExpresionContext ctx) {
        Double d1 = (Double) visit(ctx.expr());
        return d1 > 0;
    }
    
    @Override
    public Object visitF0r1(PascalParser.F0r1Context ctx) { 
        Boolean cond = (Boolean) visit(ctx.condExpr());
        while(cond){
            cond = (Boolean) visit (ctx.condExpr());
            visit (ctx.b1);
            visit (ctx.b2);
        }
        return 0d;
    }
    
    @Override
    public Object visitPrintStr(PascalParser.PrintStrContext ctx) {
        String val = ctx.STR().getText();
        System.out.println(val);
        return 0d;
    }
   
    @Override
    public Object visitPrintExpr(PascalParser.PrintExprContext ctx) {
        Object o = visit(ctx.expr());
        System.out.println(o);
        return o;
    }
    
      @Override 
    public Object visitV4rArrayTp(PascalParser.V4rArrayTpContext ctx) {
            
            Object[] list = (Object[]) visit(ctx.arraytype());
            int inicial = Integer.parseInt(list[0].toString());
            int end = Integer.parseInt(list[1].toString());
            for(int i = inicial ; i<end ; i++){
                Object[] varData = new Object[2];
                varData[0] = list[2];
                String varName = ctx.ID(0).getText()+"["+i+"]";
                SymbolsTable.getInstance().addSymbol(varName, varData);
            }    
        return 0d;
    }
    
    @Override 
    public Object visitArrayTypeIDR(PascalParser.ArrayTypeIDRContext ctx) {
        
        Object inicio = (Object) ctx.indexRange().children.get(0);
        Object end = (Object) ctx.indexRange().children.get(2);
        Object type = (Object) ctx.simpletype().getText();
        Object[] inicio_end = new Object[3] ;
        inicio_end[0] = inicio;
        inicio_end[1] = end;
        inicio_end[2] = type;
        return inicio_end;
    }
    
    
}
