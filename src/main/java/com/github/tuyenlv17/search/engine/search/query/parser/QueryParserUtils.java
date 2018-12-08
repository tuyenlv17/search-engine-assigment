package com.github.tuyenlv17.search.engine.search.query.parser;

import com.github.tuyenlv17.search.engine.document.Field;
import com.github.tuyenlv17.search.engine.search.query.BooleanQuery;
import com.github.tuyenlv17.search.engine.search.query.TermQuery;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
public class QueryParserUtils {
    public static BooleanQuery toBooleanQuery(Field field) {
        BooleanQuery booleanQuery = new BooleanQuery();
        field.getAnalyzedTokens()
                .forEach(term -> booleanQuery.should(new TermQuery(term)));
        return booleanQuery;
    }
}
