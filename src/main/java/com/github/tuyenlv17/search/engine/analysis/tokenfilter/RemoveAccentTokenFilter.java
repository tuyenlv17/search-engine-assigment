package com.github.tuyenlv17.search.engine.analysis.tokenfilter;

import com.github.tuyenlv17.search.engine.utils.TextUtils;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
public class RemoveAccentTokenFilter implements TokenFilter {

    @Override
    public String filter(String token) {
        return TextUtils.removeAccent(token);
    }
}
