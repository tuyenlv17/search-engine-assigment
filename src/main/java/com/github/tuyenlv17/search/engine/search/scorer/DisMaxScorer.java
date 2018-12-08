package com.github.tuyenlv17.search.engine.search.scorer;

import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.search.query.DisMaxQuery;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
public class DisMaxScorer extends Scorer {
    public static final Logger LOGGER = LogManager.getLogger(DisMaxScorer.class);
    DisMaxQuery disMaxQuery;
    public DisMaxScorer(DisMaxQuery query, Storage storage, Similarity similarity) {
        super(query, storage, similarity);
        this.disMaxQuery = query;
    }

    @Override
    public List<DocumentScore> scoreDoc() {
        return new ArrayList<>(scoreDocMap().keySet())
                .stream()
                .map(documentScore -> {
                    documentScore.setScore(documentScore.getScore() * disMaxQuery.getBoost());
                    return documentScore;
                })
                .sorted((o1, o2) -> Float.compare(o2.getScore(), o1.getScore()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<DocumentScore> scoreDocSet() {
        return null;
    }

    @Override
    public Map<DocumentScore, DocumentScore> scoreDocMap() {
        Map<DocumentScore, DocumentScore> documentScoreMap = new HashMap<>();
        disMaxQuery.forEach(query -> {
            Scorer subScorer = query.scorer(storage, similarity);
            Set<DocumentScore> subDocScore = subScorer.scoreDocSet();
            subDocScore.forEach(newDocumentScore -> {
                if (documentScoreMap.containsKey(newDocumentScore)) {
                    LOGGER.debug("dismax subquery {}", query);
                    DocumentScore oldDocScore = documentScoreMap.get(newDocumentScore);
                    //replace old score with higher new score
                    if (oldDocScore.getScore() < newDocumentScore.getScore()) {
                        documentScoreMap.put(newDocumentScore, newDocumentScore);
                    }
                } else {
                    documentScoreMap.put(newDocumentScore, newDocumentScore);
                }
            });
        });
        return documentScoreMap;
    }
}
