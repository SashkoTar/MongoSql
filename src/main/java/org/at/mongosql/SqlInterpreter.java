/***
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
***/
package org.at.mongosql;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.at.mongosql.adaptor.DataSourceAdaptor;
import org.at.mongosql.adaptor.MongoAdaptor;
import org.at.mongosql.grammar.SqlLexer;
import org.at.mongosql.grammar.SqlParser;
import org.at.mongosql.result.ResultSet;
import org.at.mongosql.result.Row;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlInterpreter {

    SqlConverter converter;
    DataSourceAdaptor adaptor;

    public InterpreterListener listener = // default response to messages
        new InterpreterListener() {
            public void info(String msg) { System.out.println(msg); }
            public void error(String msg) { System.err.println(msg); }
            public void error(String msg, Exception e) {
                error(msg); e.printStackTrace(System.err);
            }
            public void error(String msg, Token t) {
                error("line "+t.getLine()+": "+msg);
            }
        };

    Map<String, Object> globals = new HashMap<String, Object>();
    public Map<String, Table> tables = new HashMap<String, Table>();

    public void interp(InputStream input) throws RecognitionException, IOException {
        converter = new SqlConverter();
        adaptor = new MongoAdaptor();
        SqlLexer lex = new SqlLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        SqlParser parser = new SqlParser(tokens, this);
        parser.program();
        // System.out.println(tables);
    }


    public Object select(String name, List<Token> columns) {
        DBCursor cursor = adaptor.find("user");
        printResult(cursor);

        return new Object();
    }

    public Object select(String name, List<Token> columns, List conditions) {
        BasicDBObject dbObject = converter.handle(conditions);
        DBCursor cursor = adaptor.find("user", dbObject);
        printResult(cursor);

        return new Object();
    }

    private void printResult(DBCursor cursor) {
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

}
