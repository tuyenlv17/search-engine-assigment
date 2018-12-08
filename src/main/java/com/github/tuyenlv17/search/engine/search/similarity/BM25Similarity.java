package com.github.tuyenlv17.search.engine.search.similarity;

import com.github.tuyenlv17.search.application.config.search.BM25Config;
import com.github.tuyenlv17.search.engine.index.stat.FieldStats;
import com.github.tuyenlv17.search.engine.index.stat.TermStats;

/**
 * Created by tuyenlv17 on 2018-12-04.
 */
public class BM25Similarity extends Similarity {
    float k1;
    float b;

    /**
     * default BM25 config based on recommendation
     */
    public BM25Similarity() {
        k1 = 1.2f;
        b = 0.75f;
    }

    public BM25Similarity(BM25Config bm25Config) {
        k1 = bm25Config.getK1();
        b = bm25Config.getB();
    }

    public BM25Similarity(float k1, float b) {
        this.k1 = k1;
        this.b = b;
    }

    @Override
    public float score(int termFreq, TermStats termStats, int fieldLength, FieldStats fieldStats) {
        float tf = termFreq * (k1 + 1) / (termFreq + k1 * (1 - b + b * fieldLength / fieldStats.getAvgFieldLength()));
        int maxDocCnt = fieldStats.getMaxDocCount();
        int docFreq = termStats.getDocFreq();
        float idf = (float) Math.log(1 + (maxDocCnt - docFreq + 0.5f) / (docFreq + 0.5f));
        return tf * idf;
    }
}
