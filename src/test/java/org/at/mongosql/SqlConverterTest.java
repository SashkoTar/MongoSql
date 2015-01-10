package org.at.mongosql;

import com.mongodb.BasicDBObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by oleksandr.tarasenko on 12/24/2014.
 */
public class SqlConverterTest {

    @Test
    public void shouldConvertOneCriteria() {
        BasicCriteria criteria = new BasicCriteria("name", "=", "alex");
        List<BasicCriteria> criteriaList = new ArrayList<BasicCriteria>();
        criteriaList.add(criteria);
        List<List<BasicCriteria>> searchList = new ArrayList<List<BasicCriteria>>();
        searchList.add(criteriaList);
        SqlConverter converter = new SqlConverter();
        BasicDBObject dbObject = converter.handle(searchList);
        assertEquals(1, dbObject.toMap().size());
    }

    @Test
    public void shouldConvertTwoCriterias() {
        BasicCriteria criteria1 = new BasicCriteria("name", "=", "alex");
        BasicCriteria criteria2 = new BasicCriteria("age", "=", 15);
    }

}
