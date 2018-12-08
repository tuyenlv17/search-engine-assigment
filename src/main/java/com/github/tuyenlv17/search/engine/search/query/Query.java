package com.github.tuyenlv17.search.engine.search.query;

import com.github.tuyenlv17.search.engine.search.scorer.Scorer;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by tuyenlv17 on 2018-12-08.
 * Base query
 */
@Data
@Accessors(chain = true)
public abstract class Query {
    public abstract Scorer scorer(Storage storage, Similarity similarity);
    float boost = 1f;
}
