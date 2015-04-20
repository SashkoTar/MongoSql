package org.at.mongosql;

import org.junit.Test;

import static com.mongodb.util.MyAsserts.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/5/15
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class RelOpTest {

    @Test
    public void shouldReturnEqualRelOp() {
        assertEquals(RelOp.EQUAL, RelOp.getByToken("="));
    }

    @Test
    public void shouldReturnNotEqualRelOp() {
        assertEquals(RelOp.NOT_EQUAL, RelOp.getByToken("<>"));
    }

    @Test
    public void shouldReturnGreaterRelOp() {
        assertEquals(RelOp.GREATER, RelOp.getByToken(">"));
    }

    @Test
    public void shouldReturnLessRelOp() {
        assertEquals(RelOp.LESS, RelOp.getByToken("<"));
    }

    @Test
    public void shouldReturnLessOrEqualRelOp() {
        assertEquals(RelOp.LESS_EQUAL, RelOp.getByToken("<="));
    }

    @Test
    public void shouldReturnGreaterOrEqualRelOp() {
        assertEquals(RelOp.GREATER_EQUAL, RelOp.getByToken(">="));
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldReturnUnknownRelOp() {
        assertEquals(RelOp.UNKNOWN, RelOp.getByToken("^"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldReturnException() {
       RelOp.getByToken("^").build("name", "alex");
    }
}
