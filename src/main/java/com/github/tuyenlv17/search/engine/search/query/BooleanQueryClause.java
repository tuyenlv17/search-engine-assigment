package com.github.tuyenlv17.search.engine.search.query;

import lombok.Data;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
@Data
public class BooleanQueryClause {
    Query query;
    public static enum ClauseType {
        MUST,
        MUST_NOT,
        FILTER,
        SHOULD
    }
    ClauseType type;

    public BooleanQueryClause(Query query, ClauseType type) {
        this.query = query;
        this.type = type;
    }
}
