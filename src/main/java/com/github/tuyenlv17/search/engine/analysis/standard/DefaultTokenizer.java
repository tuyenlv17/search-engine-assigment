package com.github.tuyenlv17.search.engine.analysis.standard;

import com.github.tuyenlv17.search.engine.analysis.Tokenizer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tuyenlv17 on 2018-12-04.
 */
public class DefaultTokenizer implements Tokenizer {
    //TODO check unicode delimiter
    private static final String DELIMITER = "[\\s\\.,\"'?()-]";

    @Override
    public List<String> tokenize(String text) {
        return Arrays.asList(text.split(DELIMITER));
    }
}
