package com.github.tuyenlv17.search.engine.analysis;

import java.util.List;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
public interface Tokenizer {
    public List<String> tokenize(String text);
}
