package org.at.mongosql;

import com.mongodb.BasicDBObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 1/5/15
 * Time: 6:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class CriteriaBuilder  {
    private BasicDBObject orCriteria = new BasicDBObject();
    private List<BasicDBObject> obj = new ArrayList<BasicDBObject>();

    public CriteriaBuilder addCriteria(String name, Object value) {
        obj.add(new BasicDBObject(name, value));
        return this;
    }

    public CriteriaBuilder addCriteria(BasicDBObject criteria) {
        obj.add(criteria);
        return this;
    }

    public BasicDBObject build() {
        orCriteria.put("$or", obj);
        return orCriteria;
    }

    public BasicDBObject build(String operator) {
        orCriteria.put(operator, obj);
        return orCriteria;
    }
}
