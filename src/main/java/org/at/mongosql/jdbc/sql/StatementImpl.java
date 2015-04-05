package org.at.mongosql.jdbc.sql;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.util.Hash;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.at.mongosql.AstInterpreter;
import org.at.mongosql.adaptor.DataSourceAdaptor;
import org.at.mongosql.adaptor.MongoAdaptor;
import org.at.mongosql.grammar.SqlLexer;
import org.at.mongosql.grammar.SqlParser;
import org.at.mongosql.grammar.SqlVisitor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.sql.ResultSet;
import java.util.Map;

/**
 * Created by otarasenko on 3/13/15.
 */
public class StatementImpl implements Statement {

    DataSourceAdaptor adaptor;

    public StatementImpl(DataSourceAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    private void printResult(DBCursor cursor) {
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private InputStream convertToInputStream(String sqlStatement) {
        return new ByteArrayInputStream(sqlStatement.getBytes());
    }

    private Map<String, Object > translateRequest(String sqlRequest) throws IOException, RecognitionException {
        SqlLexer lex = new SqlLexer(new ANTLRInputStream(convertToInputStream(sqlRequest)));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        SqlParser g = new SqlParser(tokens);

        CommonTree tree = (CommonTree) g.program().getTree();
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
        AstInterpreter interpreter = new AstInterpreter();
        SqlVisitor tp = new SqlVisitor(nodes, interpreter); // create tree walker
        tp.program();
        // printResult(tp.cursor);

        return interpreter.getQueryInfo();
    }


    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            Map<String, Object> queryInfo = translateRequest(sql);
            DBCursor cursor = adaptor.find((String)queryInfo.get("collectionName"), (BasicDBObject)queryInfo.get("criteria"), (BasicDBObject)queryInfo.get("columnsForProjection"));
            return new ResultSetMongoDBImpl(cursor);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return 0;
    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String name) throws SQLException {

    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return null;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {

    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {

    }

    @Override
    public void clearBatch() throws SQLException {

    }

    @Override
    public int[] executeBatch() throws SQLException {
        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
