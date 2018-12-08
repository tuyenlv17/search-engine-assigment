package com.github.tuyenlv17.search.engine.query;

import com.github.tuyenlv17.search.engine.document.Term;
import com.github.tuyenlv17.search.engine.search.scorer.Scorer;
import com.github.tuyenlv17.search.engine.search.scorer.TermScorer;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;
import lombok.Data;

/**
 * Created by tuyenlv17 on 2018-12-08.
 * Query for single term
 */
@Data
public class TermQuery extends Query {
    Term term;

    public TermQuery(Term term) {
        this.term = term;
    }


    @Override
    public Scorer scorer(Storage storage, Similarity similarity) {
        return new TermScorer(this, storage, similarity);
    }
}
