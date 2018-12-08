package com.github.tuyenlv17.search.engine.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by tuyenlv7 on 04/12/2018.
 */
public class TextUtils {
    public static String removeAccent(String text) {
        String temp = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }
}
