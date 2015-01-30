package org.at.mongosql.facade;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;


import org.at.mongosql.grammar.GenericSql_whereLexer;
import org.at.mongosql.grammar.GenericSql_whereParser;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 1/30/15
 * Time: 1:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class SelectInterpreterTest {

    @Test
    public void runTest() throws IOException {
        InputStream input = convertToInputStream("select name from user where name='kolya' and age = 13 and age = 11 or age = 12");
        GenericSql_whereLexer lex = new GenericSql_whereLexer(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        GenericSql_whereParser g = new GenericSql_whereParser(tokens);
        try {
            g.program();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }

    private InputStream convertToInputStream(String sqlStatement) {
        return new ByteArrayInputStream(sqlStatement.getBytes());
    }
}
