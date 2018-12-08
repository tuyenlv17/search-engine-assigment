package com.github.tuyenlv17.search.engine.search.scorer;

import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.query.Query;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;

import java.util.List;
import java.util.Set;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
public class DisjunctionMaxScorer extends Scorer {

    public DisjunctionMaxScorer(Query query, Storage storage, Similarity similarity) {
        super(query, storage, similarity);
    }

    @Override
    public List<DocumentScore> scoreDoc() {
        return null;
    }

    @Override
    public Set<DocumentScore> scoreDocSet() {
        return null;
    }
}
