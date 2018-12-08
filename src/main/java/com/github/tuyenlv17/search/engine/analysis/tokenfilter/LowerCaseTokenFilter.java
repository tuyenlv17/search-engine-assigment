package com.github.tuyenlv17.search.engine.analysis.tokenfilter;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
public class LowerCaseTokenFilter implements TokenFilter {

    @Override
    public String filter(String token) {
        return token.toLowerCase();
    }
}
