package com.github.tuyenlv17.search.engine.search.scorer;

import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.search.query.Query;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tuyenlv17 on 2018-12-08.
 * Using for scoring retrieved documents using {@link com.github.tuyenlv17.search.engine.search.similarity.Similarity}
 * Each query type has a dedicated scorer
 */
public abstract class Scorer {
    Query query;
    Storage storage;
    Similarity similarity;

    public Scorer(Query query, Storage storage, Similarity similarity) {
        this.query = query;
        this.storage = storage;
        this.similarity = similarity;
    }

    public abstract List<DocumentScore> scoreDoc();
    public abstract Set<DocumentScore> scoreDocSet();
    public abstract Map<DocumentScore, DocumentScore> scoreDocMap();
}

