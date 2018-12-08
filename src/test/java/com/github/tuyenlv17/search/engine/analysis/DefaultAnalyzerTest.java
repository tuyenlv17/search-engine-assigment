package com.github.tuyenlv17.search.engine.analysis;

import com.github.tuyenlv17.search.engine.analysis.standard.DefaultAnalyzer;
import com.github.tuyenlv17.search.engine.analysis.standard.DefaultTokenizer;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
public class DefaultAnalyzerTest {
    public static void main(String[] args) {
        DefaultAnalyzer defaultDefaultAnalyzer = new DefaultAnalyzer();
        System.out.println(new DefaultTokenizer().tokenize("Sinh ra để chạy"));
        System.out.println(defaultDefaultAnalyzer.analyze("Sinh ra để chạy"));
    }
}
