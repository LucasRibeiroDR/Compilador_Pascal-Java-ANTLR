/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal;

import java.io.IOException;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import pascal.util.PascalVisitorImpl;
import pequenopascal.parser.PascalLexer;
import pequenopascal.parser.PascalParser;
import pequenopascal.parser.PascalVisitor;

/**
 *
 * @author a120065
 */
public class Principal {
    public static void main(String[] args) throws IOException {
        ANTLRInputStream input = new ANTLRFileStream("input.pascal");
        PascalLexer lexer = new PascalLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PascalParser parser = new PascalParser(tokens);
       
        ParseTree tree = parser.program();

        PascalVisitor eval = new PascalVisitorImpl();
        eval.visit(tree);
    }

}
