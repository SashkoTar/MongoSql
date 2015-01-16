package org.at.mongosql;

import com.mongodb.BasicDBObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by otarasenko on 16.01.15.
 */
public class BasicCriteriaSqlConverterTest {


    public static final String COLUMN_NAME = "age";
    public static final int COLUMN_VALUE = 15;

    SqlConverter converter;

    @Before
    public void init() {
        converter = new SqlConverter();
    }

    @Test
    public void shouldConvertOneCriteria() {
        BasicDBObject dbObject = convertToSingleBasicDBObject(COLUMN_NAME, "=", COLUMN_VALUE);
        assertTrue(dbObject.containsField(COLUMN_NAME));
        assertTrue(dbObject.size() == 1);
    }

    @Test
    public void shouldConvertOneCriteriaWithGreaterCompare() {
        BasicDBObject dbObject = convertToSingleBasicDBObject(COLUMN_NAME, ">", COLUMN_VALUE);
        BasicDBObject innerObject = (BasicDBObject) dbObject.get(COLUMN_NAME);
        assertTrue(innerObject.containsField("$gt"));
        assertTrue(innerObject.size() == 1);
    }

    @Test
    public void shouldConvertOneCriteriaWithGreaterEqualCompare() {
        BasicDBObject dbObject = convertToSingleBasicDBObject(COLUMN_NAME, ">=", COLUMN_VALUE);
        BasicDBObject innerObject = (BasicDBObject) dbObject.get(COLUMN_NAME);
        assertTrue(innerObject.containsField("$gte"));
        assertTrue(innerObject.size() == 1);
    }

    @Test
    public void shouldConvertOneCriteriaWithLessCompare() {
        BasicDBObject dbObject = convertToSingleBasicDBObject(COLUMN_NAME, "<", COLUMN_VALUE);
        BasicDBObject innerObject = (BasicDBObject) dbObject.get(COLUMN_NAME);
        assertTrue(innerObject.containsField("$lt"));
        assertTrue(innerObject.size() == 1);
    }

    @Test
    public void shouldConvertOneCriteriaWithLessEqualCompare() {
        BasicDBObject dbObject = convertToSingleBasicDBObject(COLUMN_NAME, "<=", COLUMN_VALUE);
        BasicDBObject innerObject = (BasicDBObject) dbObject.get(COLUMN_NAME);
        assertTrue(innerObject.containsField("$lte"));
        assertTrue(innerObject.size() == 1);
    }

    @Test
    public void shouldConvertOneCriteriaWithNotEqualCompare() {
        BasicDBObject dbObject = convertToSingleBasicDBObject(COLUMN_NAME, "<>", COLUMN_VALUE);
        BasicDBObject innerObject = (BasicDBObject) dbObject.get(COLUMN_NAME);
        assertTrue(innerObject.containsField("$ne"));
        assertTrue(innerObject.size() == 1);
    }

    private BasicDBObject convertToSingleBasicDBObject(String name, String operator, Object value) {
        List<BasicCriteria> criteriaList = new ArrayList<BasicCriteria>();
        criteriaList.add(buildBasicCriteria(name, operator, value));
        List<List<BasicCriteria>> searchList = new ArrayList<List<BasicCriteria>>();
        searchList.add(criteriaList);
        return converter.handle(searchList);
    }

    private BasicCriteria buildBasicCriteria(String name, String operator, Object value) {
        return new BasicCriteria(name, operator, value);
    }
}
