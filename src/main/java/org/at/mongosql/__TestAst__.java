package org.at.mongosql;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.at.mongosql.grammar.*;

/**
 * Created by otarasenko on 3/10/15.
 */
public class __TestAst__ {


    public static void main(String args[]) throws Exception {
        SqlLexer lex = new SqlLexer(new ANTLRFileStream("C:\\Projects\\NonJob\\Grammar\\MongoSql\\input\\sql_input_2.txt", "UTF8"));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        SqlParser g = new SqlParser(tokens);
        try {
            CommonTree tree = (CommonTree) g.program().getTree();
            CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
            SqlVisitor tp = new SqlVisitor(nodes); // create tree walker
            tp.program();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }
}
