grammar Sql;

@parser::header {
   package org.at.mongosql.grammar;
   import org.at.mongosql.*;
   import java.util.Map;
   import java.util.HashMap;
}

@lexer::header {
   package org.at.mongosql.grammar;
}

@members {
    SqlInterpreter interp;
    public SqlParser(TokenStream input, SqlInterpreter interp) {
        this(input);
        this.interp = interp;
    }
    
    public void initInterpreter(SqlInterpreter interp) {
    	this.interp = interp;
    }
}

program
    :   stat+
    ;

stat:   
        select_query
    ;



select_query
scope {
   List searchConditions;	
}
@init {
	$select_query::searchConditions = new ArrayList();	
}
    :   'select' columns+=ID (',' columns+=ID)* 'from' tbl=ID
        (   'where' sc=subCondition {$select_query::searchConditions.add($sc.value);} (('or') sc2=subCondition {$select_query::searchConditions.add($sc2.value);})*  {interp.select($tbl.text, $columns, $select_query::searchConditions);}
        |   {interp.select($tbl.text, $columns);}
        )
    ;


subCondition returns [List<BasicCriteria> value]
scope {
	List<BasicCriteria> basicCriteriaList;	
}
@init {
	$subCondition::basicCriteriaList =  new ArrayList<BasicCriteria>();
}
    :   bc=basicCriteria {$subCondition::basicCriteriaList.add($bc.value);} ('and' bc2=basicCriteria {$subCondition::basicCriteriaList.add($bc2.value);})*  {$value = $subCondition::basicCriteriaList; }    	
    ;
    
basicCriteria returns [BasicCriteria value]
	:	ID OPERATOR expr { $value = new BasicCriteria($ID.text, $OPERATOR.text, $expr.value);} 
	;

// START: expr
// Match a simple value or do a query
expr returns [Object value] // access as $expr.value in other rules
    :   INT     {$value = $INT.int;}
    |   STRING  {$value = $STRING.text;}
    ;
// END: expr

OPERATOR : '='|'<>'|'>'|'<'|'>='|'<=';

ID  :   ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;

INT :   '0'..'9'+ ;

STRING
    :   '\'' ~'\''* '\''
        {setText(getText().substring(1, getText().length()-1));}
    ;

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;
