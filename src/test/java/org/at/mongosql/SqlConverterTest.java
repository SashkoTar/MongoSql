package org.at.mongosql;

import com.mongodb.BasicDBObject;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by oleksandr.tarasenko on 12/24/2014.
 */
public class SqlConverterTest {

    SqlConverter converter;

    @Before
    public void init() {
        converter = new SqlConverter();
    }






    @Test
    public void shouldConvertTwoCriterias() {
        List<BasicCriteria> criteriaList = new ArrayList<BasicCriteria>();
        criteriaList.add(buildBasicCriteria("name", "=", "alex"));
        criteriaList.add(buildBasicCriteria("age", "=", 15));
        List<List<BasicCriteria>> searchList = new ArrayList<List<BasicCriteria>>();
        searchList.add(criteriaList);
        BasicDBObject dbObject = converter.handle(searchList);
        assertTrue(dbObject.containsField("$and"));
        assertTrue(dbObject.size() == 1);

    }

    @Test
    public void shouldConvertToOneOr() {
        List<BasicCriteria> criteriaList = new ArrayList<BasicCriteria>();
        criteriaList.add(buildBasicCriteria("name", "=", "alex"));
        criteriaList.add(buildBasicCriteria("age", "=", 15));


        List<BasicCriteria> criteriaList2 = new ArrayList<BasicCriteria>();
        criteriaList2.add(buildBasicCriteria("age", "=", 17));

        List<List<BasicCriteria>> searchList = new ArrayList<List<BasicCriteria>>();
        searchList.add(criteriaList);
        searchList.add(criteriaList2);


        BasicDBObject dbObject = converter.handle(searchList);
        assertTrue(dbObject.containsField("$or"));
        assertTrue(dbObject.size() == 1);

        assertEquals(2, ((List) dbObject.get("$or")).size());

    }

    private BasicCriteria buildBasicCriteria(String name, String operator, Object value) {
        return new BasicCriteria(name, operator, value);
    }
}
