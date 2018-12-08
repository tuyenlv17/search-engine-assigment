package com.github.tuyenlv17.search.engine.analysis.standard;

import com.github.tuyenlv17.search.engine.analysis.Analyzer;
import com.github.tuyenlv17.search.engine.analysis.Tokenizer;
import com.github.tuyenlv17.search.engine.analysis.tokenfilter.LowerCaseTokenFilter;
import com.github.tuyenlv17.search.engine.analysis.tokenfilter.TokenFilter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tuyenlv17 on 2018-12-04.
 */
@Service
public class DefaultAnalyzer extends Analyzer {

    public DefaultAnalyzer() {
        this(new DefaultTokenizer(), Arrays.asList(
                new LowerCaseTokenFilter()));
    }

    public DefaultAnalyzer(Tokenizer tokenizer, List<TokenFilter> tokenFilters) {
        super(tokenizer, tokenFilters);
    }
}
