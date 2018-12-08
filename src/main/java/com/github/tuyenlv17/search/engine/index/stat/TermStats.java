package com.github.tuyenlv17.search.engine.index.stat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by tuyenlv17 on 2018-12-05.
 * Stats info form term: doc freq, per term boosting
 */
@Data
@Accessors(chain = true)
public class TermStats {
    int docFreq;
}
