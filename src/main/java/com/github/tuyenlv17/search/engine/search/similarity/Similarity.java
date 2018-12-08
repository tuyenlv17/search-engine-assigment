package com.github.tuyenlv17.search.engine.search.similarity;

import com.github.tuyenlv17.search.engine.index.stat.FieldStats;
import com.github.tuyenlv17.search.engine.index.stat.TermStats;

/**
 * Created by tuyenlv17 on 2018-12-04.
 */
public abstract class Similarity {
    public abstract float score(int termFreq, TermStats termStats, int fieldLength, FieldStats fieldStats);
}
