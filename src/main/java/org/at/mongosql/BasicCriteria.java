package org.at.mongosql;

/**
 * Created by oleksandr.tarasenko on 12/24/2014.
 */
public class BasicCriteria {
    private String column;
    private String operator;
    private Object value;

    public BasicCriteria(String column, String operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }


    public String getOperator() {
        return operator;
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }
}
