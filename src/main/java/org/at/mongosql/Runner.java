/***
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
 ***/
package org.at.mongosql;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.at.mongosql.grammar.SqlLexer;
import org.at.mongosql.grammar.SqlParser;
import org.at.mongosql.grammar.SqlVisitor;

import java.io.*;

public class Runner {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sqlLine;
        Runner runner = new Runner();
        System.out.print("Enter Sql Request > ");
        boolean nextStatement = true;
        do {
            try {
                sqlLine = br.readLine();
                if (sqlLine.equals("quit")) {
                    nextStatement = false;
                } else {
                    runner.translateRequest(sqlLine);
                }
            } catch (IOException ioe) {
                System.out.println("IO error trying to read your name!");
                System.exit(1);
            }
        } while (nextStatement);
    }

    private InputStream convertToInputStream(String sqlStatement) {
        return new ByteArrayInputStream(sqlStatement.getBytes());
    }

    private void translateRequest(String sqlRequest) throws IOException {
        SqlLexer lex = new SqlLexer(new ANTLRInputStream(convertToInputStream(sqlRequest)));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        SqlParser g = new SqlParser(tokens);
        try {
            CommonTree tree = (CommonTree) g.program().getTree();
            CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
            SqlVisitor tp = new SqlVisitor(nodes); // create tree walker
            tp.program();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }
}


