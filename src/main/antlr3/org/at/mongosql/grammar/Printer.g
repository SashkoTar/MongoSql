// START: header
tree grammar Printer;                   // this grammar is a tree grammar called Printer
options {
    tokenVocab=GenericSqlASTWhere;      // use token vocabulary from VecMath.g
    ASTLabelType=CommonTree;             // use homogeneous CommonTree for $ID, etc.
}
@header {
   package org.at.mongosql.grammar;
}
@members { void print(String s) { System.out.print(s); } }
// END: header

// START: prog
//prog:   stat+ ; // match list of statement subtrees
// match trees like ('=' x 1) and ('print' ('+' 3 4))
//stat:   ^('=' ID  {print($ID.text+" = ");} expr) {print("\n");}
 //   |   ^('print' {print("print ");}       expr) {print("\n");}
 //   ;
// END: prog

// START: expr
//expr:   ^('+' expr {print("+");} expr)
 //   |   ^('*' expr {print("*");} expr)
  //  |   ^('.' expr {print(".");} expr)
//    |   ^(VEC {print("[");} expr ({print(", ");} expr)* {print("]");})
 //   |   INT {print($INT.text);}
 //   |   ID  {print($ID.text);}
  //  ;
// END: expr



program
    :   stat+
    ;

stat:
        select_query
    ;

select_query
    	:    ^(SELECT ^(FROM ID) columnList ^(WHERE conditionList))  {print($ID.text);}
    	;

columnList
	: ^(COLUMNS ID+)
	;

nestedCondition
	:  conditionList
	;

conditionList
    	: ^(OR_COND conjunction+)
    	;


conjunction
	: ^(AND_COND condition+)
	;

condition
	: comparison
	| nestedCondition
	;

comparison
	: ^(RELOP OPERATOR ID INT)
	| ^(RELOP OPERATOR ID STRING)
	;


// START: expr
// Match a simple value or do a query
//expr
//    :   ^(INT)
//    |   ^(STRING)
//    ;
// END: expr