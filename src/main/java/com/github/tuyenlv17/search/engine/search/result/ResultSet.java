package com.github.tuyenlv17.search.engine.search.result;

import com.github.tuyenlv17.search.engine.document.DocumentScore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuyenlv17 on 2018-12-08.
 * Wrapper for results
 */
@Data
@Accessors(chain = true)
public class ResultSet {
    List<DocumentScore> documentScores;
    int totalResults;
    int from;
    int size;

    public ResultSet(List<DocumentScore> documentScores, int from, int size) {
        this.totalResults = documentScores.size();
        if (from > documentScores.size()) {
            this.documentScores = new ArrayList<>();
        } else {
            int to = from + size;
            to = to > documentScores.size() ? documentScores.size() : to;
            this.documentScores = documentScores.subList(from, to);
        }
        this.from = from;
        this.size = size;
    }
}
