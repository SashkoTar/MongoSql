// START: header
tree grammar SqlVisitor;                   // this grammar is a tree grammar called Printer
options {
    tokenVocab=Sql;      // use token vocabulary from VecMath.g
    ASTLabelType=CommonTree;             // use homogeneous CommonTree for $ID, etc.
}
@header {
   package org.at.mongosql.grammar;
      import org.at.mongosql.*;
      import com.mongodb.BasicDBObject;
      import com.mongodb.DBCursor;
}
@members {
  AstInterpreter interp;
   void print(String s) { System.out.print(s); }
   public DBCursor cursor;
}
// END: header


program
@init {
	interp = new AstInterpreter();
}
    :   select_query
    ;

select_query
    	:    ^(SELECT ^(FROM ID) columnList ^(WHERE conditionList))  {cursor=interp.select($ID.text, $conditionList.value);}
    	;

columnList
	: ^(COLUMNS (columnNames+=ID)+) {interp.setColumnsForProjection($columnNames); }
	| ^(COLUMNS '*') {interp.setAllColumnsForProjection();}
	;

nestedCondition returns [BasicDBObject value]
	:  conditionList {$value = $conditionList.value; }
	;

conditionList returns [BasicDBObject value]
scope {
	List<BasicDBObject> basicCriteriaList;
}
@init {
	$conditionList::basicCriteriaList =  new ArrayList<BasicDBObject>();
}
    	: ^(OR_COND (conjunction {$conditionList::basicCriteriaList.add($conjunction.value);})+) {$value = interp.handleOrCondition($conditionList::basicCriteriaList);}
    	;


conjunction returns [BasicDBObject value]
scope {
	List<BasicDBObject> basicCriteriaList;
}
@init {
	$conjunction::basicCriteriaList =  new ArrayList<BasicDBObject>();
}
	: ^(AND_COND (condition {$conjunction::basicCriteriaList.add($condition.value);})+) {$value = interp.handleAndCondition($conjunction::basicCriteriaList);}
	;

condition returns [BasicDBObject value]
	: comparison {$value = $comparison.value;}
	| nestedCondition {$value = $nestedCondition.value;}
	;

comparison returns [BasicDBObject value]
	: ^(RELOP OPERATOR ID INT) { $value = interp.handleBasicCriteria($ID.text, $OPERATOR.text, $INT.int);}
	| ^(RELOP OPERATOR ID STRING){ $value = interp.handleBasicCriteria($ID.text, $OPERATOR.text, $STRING.text);}
	;
