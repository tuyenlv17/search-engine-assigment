package com.github.tuyenlv17.search.engine.search.scorer;

import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.search.query.BooleanQuery;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
public class BooleanScorer extends Scorer {
    BooleanQuery booleanQuery;
    public BooleanScorer(BooleanQuery query, Storage storage, Similarity similarity) {
        super(query, storage, similarity);
        this.booleanQuery = query;
    }

    @Override
    public List<DocumentScore> scoreDoc() {
        return new ArrayList<>(scoreDocMap().keySet())
                .stream()
                .sorted((o1, o2) -> Float.compare(o2.getScore(), o1.getScore()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<DocumentScore> scoreDocSet() {
        return scoreDocMap().keySet();
    }

    @Override
    public Map<DocumentScore, DocumentScore> scoreDocMap() {
        Map<DocumentScore, DocumentScore> documentScoreMap = new HashMap<>();
        booleanQuery.forEach(booleanQueryClause -> {
            Scorer subScorer = booleanQueryClause.getQuery().scorer(storage, similarity);
            Set<DocumentScore> subDocScore = subScorer.scoreDocSet();
            subDocScore.forEach(documentScore -> {
                if (documentScoreMap.containsKey(documentScore)) {
                    DocumentScore oldDocScore = documentScoreMap.get(documentScore);
                    float newScore = documentScore.getScore() + oldDocScore.getScore();
                    oldDocScore.setScore(newScore);
                } else {
                    documentScoreMap.put(documentScore, documentScore);
                }
            });
        });
        return documentScoreMap;
    }
}
