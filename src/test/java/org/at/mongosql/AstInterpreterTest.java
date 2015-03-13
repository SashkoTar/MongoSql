package org.at.mongosql;

import com.mongodb.BasicDBObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by otarasenko on 3/13/15.
 */
public class AstInterpreterTest {

    public static final String COLUMN_NAME = "age";
    public static final int COLUMN_VALUE = 15;

    @Test
    public void shouldCreateBasicDBObject() {
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

    @Test
    public void shouldConvertTwoCriteriaToOrCondition() {
        AstInterpreter interpreter = new AstInterpreter();
        List<BasicDBObject> criteriaList = new ArrayList<BasicDBObject>();
        criteriaList.add(interpreter.handleBasicCriteria(COLUMN_NAME, "<>", COLUMN_VALUE));
        criteriaList.add(interpreter.handleBasicCriteria(COLUMN_NAME, "=", COLUMN_VALUE));
        BasicDBObject innerObject =  interpreter.handleOrCondition(criteriaList);
        assertTrue(innerObject.containsField("$or"));
        assertTrue(innerObject.size() == 1);
        assertTrue(((List)innerObject.get("$or")).size() == 2);
    }

    @Test
    public void shouldConvertTwoCriteriaToAndCondition() {
        AstInterpreter interpreter = new AstInterpreter();
        List<BasicDBObject> criteriaList = new ArrayList<BasicDBObject>();
        criteriaList.add(interpreter.handleBasicCriteria(COLUMN_NAME, "<>", COLUMN_VALUE));
        criteriaList.add(interpreter.handleBasicCriteria(COLUMN_NAME, "=", COLUMN_VALUE));
        BasicDBObject innerObject =  interpreter.handleAndCondition(criteriaList);
        assertTrue(innerObject.containsField("$and"));
        assertTrue(innerObject.size() == 1);
        assertTrue(((List)innerObject.get("$and")).size() == 2);
    }

    private BasicDBObject convertToSingleBasicDBObject(String name, String operator, Object value) {
        AstInterpreter interpreter = new AstInterpreter();
        return interpreter.handleBasicCriteria(name, operator, value);
    }

}
