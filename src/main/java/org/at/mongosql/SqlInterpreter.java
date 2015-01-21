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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SqlInterpreter {

    SqlConverter converter;
    DataSourceAdaptor adaptor;


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
        DBCursor cursor = adaptor.find(name, new BasicDBObject(), handleColumns(columns));
        printResult(cursor);

        return new Object();
    }

    public Object select(String name, List<Token> columns, List conditions) {
        BasicDBObject dbObject = converter.handle(conditions);
        DBCursor cursor = adaptor.find(name, dbObject, handleColumns(columns));
        printResult(cursor);

        return new Object();
    }

    private void printResult(DBCursor cursor) {
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private BasicDBObject handleColumns(List<Token> columns) {
        BasicDBObject fields = new BasicDBObject();
        if(columns.size() == 1 && columns.get(0).getText().equals("*")) {
            return fields;
        }
        for(Token field : columns) {
            fields.append(field.getText(), 1);
        }
        return fields;
    }

}
