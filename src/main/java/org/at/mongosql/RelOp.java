package org.at.mongosql;

import com.mongodb.BasicDBObject;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/5/15
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public enum RelOp {

    EQUAL("=")  {
        BasicDBObject build(String id, Object value) { return new BasicDBObject(id, value); }
    },
    NOT_EQUAL("<>"){
        BasicDBObject build(String id, Object value) { return new BasicDBObject(id, new BasicDBObject("$ne", value)); }
    },
    GREATER(">"){
        BasicDBObject build(String id, Object value) { return new BasicDBObject(id, new BasicDBObject("$gt", value)); }
    },
    GREATER_EQUAL(">="){
        BasicDBObject build(String id, Object value) { return new BasicDBObject(id, new BasicDBObject("$gte", value)); }
    },
    LESS("<"){
        BasicDBObject build(String id, Object value) { return new BasicDBObject(id, new BasicDBObject("$lt", value)); }
    },
    LESS_EQUAL("<="){
        BasicDBObject build(String id, Object value) { return new BasicDBObject(id, new BasicDBObject("$lte", value)); }
    },
    UNKNOWN("UNKNOWN"){
        BasicDBObject build(String id, Object value) {  throw new IllegalArgumentException("Operation " + tokenText + " is not supported"); }
    };

    String tokenText;

    RelOp(String tokenText) {
        this.tokenText = tokenText;
    }

    public static RelOp getByToken(String tokenText) {
      for (RelOp operation : values()) {
         if(operation.tokenText.equals(tokenText)) {
             return operation;
         }
      }
        throw new IllegalArgumentException("Operation " + tokenText + " is not supported");
    }

    abstract BasicDBObject build(String id, Object value);
}
