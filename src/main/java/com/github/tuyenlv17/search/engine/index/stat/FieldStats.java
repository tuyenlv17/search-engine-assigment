package com.github.tuyenlv17.search.engine.index.stat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@Data
@Accessors(chain = true)
public class FieldStats {
    int docCount;
    int totalTermFreq;

    public FieldStats(int docCount, int totalTermFreq) {
        this.docCount = docCount;
        this.totalTermFreq = totalTermFreq;
    }

    public float getAvgFieldLength() {
        return this.getTotalTermFreq() / (float)this.getDocCount();
    }
}
