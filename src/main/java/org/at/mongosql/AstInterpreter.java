package org.at.mongosql;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.at.mongosql.adaptor.DataSourceAdaptor;
import org.at.mongosql.adaptor.MongoAdaptor;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by otarasenko on 3/12/15.
 */
public class AstInterpreter {

    DataSourceAdaptor adaptor;

    private BasicDBObject columnsForProjection;

    public AstInterpreter() {
        try{
            adaptor = new MongoAdaptor();
        }catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    private void log(Object info) {
        System.out.println(info);
    }

    private void printResult(DBCursor cursor) {
        while(cursor.hasNext()) {
            log(cursor.next());
        }
    }

    public DBCursor select(String collectionName, BasicDBObject criteria) {
        DBCursor cursor = adaptor.find(collectionName, criteria, columnsForProjection);
       // printResult(cursor);
        return cursor;
    }


    public void setColumnsForProjection(List<CommonTree> columns) {
        BasicDBObject fields = new BasicDBObject();
        if(columns.size() == 0) {
            throw new IllegalArgumentException("Column List Can't Be Empty");
        }
        for(CommonTree field : columns) {
            fields.append(field.getText(), 1);
        }
        //fields.append("_id", 0);
        this.columnsForProjection = fields;
    }


    public void setAllColumnsForProjection() {
        this.columnsForProjection = new BasicDBObject();
    }


    public BasicDBObject handleAndCondition(List<BasicDBObject> criterias) {
        CriteriaBuilder builderOr = new CriteriaBuilder();
        for (BasicDBObject condition : criterias) {
            builderOr.addCriteria(condition);
        }
        return builderOr.build("$and");
    }

    public BasicDBObject handleOrCondition(List<BasicDBObject> criterias) {
            CriteriaBuilder builderOr = new CriteriaBuilder();
            for (BasicDBObject condition : criterias) {
                builderOr.addCriteria(condition);
            }
            return builderOr.build("$or");
    }

    //TODO  Refactor to use Enum
    public BasicDBObject handleBasicCriteria(String id, String operator, Object value) {
        if(operator.equals(">")) {
            return new BasicDBObject(id, new BasicDBObject("$gt", value));
        }
        if(operator.equals(">=")) {
            return new BasicDBObject(id, new BasicDBObject("$gte", value));
        }
        if(operator.equals("<")) {
            return new BasicDBObject(id, new BasicDBObject("$lt", value));
        }
        if(operator.equals("<=")) {
            return new BasicDBObject(id, new BasicDBObject("$lte", value));
        }
        if(operator.equals("<>")) {
            return new BasicDBObject(id, new BasicDBObject("$ne", value));
        }
        return new BasicDBObject(id, value);
    }

}
