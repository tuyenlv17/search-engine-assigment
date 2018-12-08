package com.github.tuyenlv17.search.engine.document;

import com.github.tuyenlv17.search.engine.analysis.Analyzer;
import com.github.tuyenlv17.search.engine.analysis.standard.DefaultAnalyzer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by tuyenlv17 on 2018-12-04.
 */
@Data
@Accessors(chain = true)
public class Field {
    String name;
    Object value;
    String index;
    List<Term> analyzedTokens;
    Analyzer analyzer;

    public Field(String index, String name, Object value) {
        this.name = name;
        this.value = value;
        analyzer = new DefaultAnalyzer();
        analyzedTokens = analyzer.analyze(value.toString())
                .stream()
                .map(s -> new Term(index, name, s))
                .collect(Collectors.toList());
    }
}
