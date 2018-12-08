package com.github.tuyenlv17.search.engine.search.query;

import com.github.tuyenlv17.search.engine.search.scorer.DisMaxScorer;
import com.github.tuyenlv17.search.engine.search.scorer.Scorer;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
public class DisMaxQuery extends Query implements Iterable<Query> {

    float tiebreak;

    List<Query> queries = new ArrayList<>();

    @Override
    public Scorer scorer(Storage storage, Similarity similarity) {
        return new DisMaxScorer(this, storage, similarity);
    }

    @Override
    public Iterator<Query> iterator() {
        return queries.iterator();
    }

    /**
     * add new query to current {@link DisMaxQuery}
     * @param query
     * @return current {@link DisMaxQuery}
     */
    public DisMaxQuery add(Query query) {
        queries.add(query);
        return this;
    }
}
