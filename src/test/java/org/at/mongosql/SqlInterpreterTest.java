package org.at.mongosql;

import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by oleksandr.tarasenko on 12/15/2014.
 */
public class SqlInterpreterTest {

    private SqlInterpreter instance;

    @Before
    public void init() {
        instance = spy(new SqlInterpreter());
    }

    @Test
    public void shouldInvokeSelect() throws IOException, RecognitionException {
        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
        doReturn(new Object()).when(instance).select(anyString(), anyList());
        instance.interp(convertToInputStream("select name from user"));
        verify(instance, times(1)).select(eq("user"), argument.capture());
        assertEquals(1, argument.getValue().size());
    }


    @Test
    public void shouldInvokeSelectWithManyParameter() throws IOException, RecognitionException {
        ArgumentCaptor<List> columns = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<List> conditions = ArgumentCaptor.forClass(List.class);
        doReturn(new Object()).when(instance).select(anyString(), anyList(), anyList());
        instance.interp(convertToInputStream("select name from user where name='kolya' and age = 13 and age = 11 or age = 12"));
        verify(instance).select(eq("user"), columns.capture(), conditions.capture());
        assertEquals(1, columns.getValue().size());
        assertEquals(2, conditions.getValue().size());
    }

    @Test
    public void shouldInvokeSelectWithManyParameterAndPassSearchCriteria() throws IOException, RecognitionException {
        ArgumentCaptor<List> conditions = ArgumentCaptor.forClass(List.class);
        doReturn(new Object()).when(instance).select(anyString(), anyList(), anyList());
        instance.interp(convertToInputStream("select name from user where name='kolya' and age = 13 and age = 11 or age = 12"));
        verify(instance).select(eq("user"), anyList(), conditions.capture());
        List<BasicCriteria> list = (List)conditions.getValue().get(0);
        assertEquals(3, list.size());
    }

    private InputStream convertToInputStream(String sqlStatement) {
        return new ByteArrayInputStream(sqlStatement.getBytes());
    }


}
