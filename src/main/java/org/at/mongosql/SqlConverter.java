package org.at.mongosql;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.List;

/**
 * Created by oleksandr.tarasenko on 12/24/2014.
 */
public class SqlConverter {
    public BasicDBObject handle(List<List<BasicCriteria>> searchList) {
        if (isOneElementInside(searchList)) {
            return createAndCondition(searchList.get(0));
        } else {
            CriteriaBuilder builderOr = new CriteriaBuilder();
            for (List<BasicCriteria> condition : searchList) {
                builderOr.addCriteria(createAndCondition(condition));
            }
            return builderOr.build("$or");
        }
    }

    private BasicDBObject createAndCondition(List<BasicCriteria> andCondition) {
        if (isOneElementInside(andCondition)) {
            return convertToBasicDBObject(andCondition.get(0));
        } else {
            CriteriaBuilder builder = new CriteriaBuilder();
            for (BasicCriteria criteria : andCondition) {
                builder.addCriteria(convertToBasicDBObject(criteria));
            }
            return builder.build("$and");
        }
    }

    private boolean isOneElementInside(List list) {
        return list.size() == 1;
    }

    private BasicDBObject convertToBasicDBObject(BasicCriteria criteria) {
        if(criteria.getOperator().equals(">")) {
            return new BasicDBObject(criteria.getColumn(), new BasicDBObject("$gt", criteria.getValue()));
        }
        if(criteria.getOperator().equals(">=")) {
            return new BasicDBObject(criteria.getColumn(), new BasicDBObject("$gte", criteria.getValue()));
        }
        if(criteria.getOperator().equals("<")) {
            return new BasicDBObject(criteria.getColumn(), new BasicDBObject("$lt", criteria.getValue()));
        }
        if(criteria.getOperator().equals("<=")) {
            return new BasicDBObject(criteria.getColumn(), new BasicDBObject("$lte", criteria.getValue()));
        }
        if(criteria.getOperator().equals("<>")) {
            return new BasicDBObject(criteria.getColumn(), new BasicDBObject("$ne", criteria.getValue()));
        }

        return new BasicDBObject(criteria.getColumn(), criteria.getValue());

    }
}
