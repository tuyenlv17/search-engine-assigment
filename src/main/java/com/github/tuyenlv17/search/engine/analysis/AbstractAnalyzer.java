package com.github.tuyenlv17.search.engine.analysis;

import com.github.tuyenlv17.search.engine.analysis.tokenfilter.TokenFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
public class AbstractAnalyzer {
    Tokenizer tokenizer;
    List<TokenFilter> tokenFilters;

    public AbstractAnalyzer() {
    }

    public AbstractAnalyzer(Tokenizer tokenizer, List<TokenFilter> tokenFilters) {
        this.tokenizer = tokenizer;
        this.tokenFilters = tokenFilters;
    }

    public List<String> analyze(String text) {
        return tokenizer.tokenize(text)
                .stream()
                .map(s -> {
                    for (TokenFilter tokenFilter : tokenFilters) {
                        s = tokenFilter.filter(s);
                    }
                    return s;
                })
                .collect(Collectors.toList());
    }
}
