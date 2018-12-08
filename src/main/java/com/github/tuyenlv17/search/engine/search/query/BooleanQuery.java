package com.github.tuyenlv17.search.engine.search.query;

import com.github.tuyenlv17.search.engine.search.scorer.BooleanScorer;
import com.github.tuyenlv17.search.engine.search.scorer.Scorer;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.tuyenlv17.search.engine.search.query.BooleanQueryClause.ClauseType.SHOULD;
/**
 * Created by tuyenlv17 on 2018-12-08.
 */
public class BooleanQuery extends Query implements Iterable<BooleanQueryClause> {
    List<BooleanQueryClause> queryClauses = new ArrayList<>();

    @Override
    public Scorer scorer(Storage storage, Similarity similarity) {
        return new BooleanScorer(this, storage, similarity);
    }

    @Override
    public Iterator<BooleanQueryClause> iterator() {
        return queryClauses.iterator();
    }

    /**
     * Add should clause
     * @param query
     * @return current {@link BooleanQuery}
     */
    public BooleanQuery should(Query query) {
        queryClauses.add(new BooleanQueryClause(query, SHOULD));
        return this;
    }
}
