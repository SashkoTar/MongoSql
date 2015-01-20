package org.at.mongosql;

import java.io.*;
import org.antlr.runtime.*;
import org.antlr.runtime.debug.DebugEventSocketProxy;

import org.at.mongosql.grammar.*;


public class __Test__ {

    public static void main(String args[]) throws Exception {
        SqlLexer lex = new SqlLexer(new ANTLRFileStream("C:\\Projects\\NonJob\\Grammar\\MongoSql\\input\\sql_input_2.txt", "UTF8"));
        CommonTokenStream tokens = new CommonTokenStream(lex);

   //     SqlParser g = new SqlParser(tokens, 49100, null);
    //    g.initInterpreter(new SqlInterpreter());
        SqlParser g = new SqlParser(tokens, new SqlInterpreter());
        try {
            g.program();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }
}