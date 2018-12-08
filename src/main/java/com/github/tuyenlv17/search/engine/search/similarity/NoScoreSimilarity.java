package com.github.tuyenlv17.search.engine.search.similarity;

import com.github.tuyenlv17.search.engine.index.stat.FieldStats;
import com.github.tuyenlv17.search.engine.index.stat.TermStats;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
public class NoScoreSimilarity extends Similarity {

    @Override
    public float score(int termFreq, TermStats termStats, int fieldLength, FieldStats fieldStats) {
        return 0;
    }
}
