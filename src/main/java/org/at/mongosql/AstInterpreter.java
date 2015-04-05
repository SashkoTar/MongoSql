package org.at.mongosql;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.at.mongosql.adaptor.DataSourceAdaptor;
import org.at.mongosql.adaptor.MongoAdaptor;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by otarasenko on 3/12/15.
 */
public class AstInterpreter {

    private Map<String, Object> queryInfo = new HashMap<String, Object>();

    private BasicDBObject columnsForProjection;

    private void log(Object info) {
        System.out.println(info);
    }

    private void printResult(DBCursor cursor) {
        while (cursor.hasNext()) {
            log(cursor.next());
        }
    }

    public Map<String, Object> getQueryInfo() {
        return queryInfo;
    }

    public Map<String, Object> select(String collectionName, BasicDBObject criteria) {
        this.queryInfo.put("queryType", "select");
        this.queryInfo.put("collectionName", collectionName);
        this.queryInfo.put("criteria", criteria);
        this.queryInfo.put("columnsForProjection", columnsForProjection);
        return this.queryInfo;
    }


    public void setColumnsForProjection(List<CommonTree> columns) {
        BasicDBObject fields = new BasicDBObject();
        if (columns.size() == 0) {
            throw new IllegalArgumentException("Column List Can't Be Empty");
        }
        for (CommonTree field : columns) {
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
        if (operator.equals(">")) {
            return new BasicDBObject(id, new BasicDBObject("$gt", value));
        }
        if (operator.equals(">=")) {
            return new BasicDBObject(id, new BasicDBObject("$gte", value));
        }
        if (operator.equals("<")) {
            return new BasicDBObject(id, new BasicDBObject("$lt", value));
        }
        if (operator.equals("<=")) {
            return new BasicDBObject(id, new BasicDBObject("$lte", value));
        }
        if (operator.equals("<>")) {
            return new BasicDBObject(id, new BasicDBObject("$ne", value));
        }
        return new BasicDBObject(id, value);
    }

}
