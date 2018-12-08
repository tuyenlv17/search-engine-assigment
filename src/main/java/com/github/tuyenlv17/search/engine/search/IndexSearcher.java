package com.github.tuyenlv17.search.engine.search;

import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.search.query.Query;
import com.github.tuyenlv17.search.engine.search.result.ResultSet;
import com.github.tuyenlv17.search.engine.search.scorer.Scorer;
import com.github.tuyenlv17.search.engine.search.similarity.NoScoreSimilarity;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
public class IndexSearcher {
    Similarity similarity;
    Storage storage;

    public IndexSearcher() {
    }

    public IndexSearcher(Storage storage, Similarity similarity) {
        this.storage = storage;
        this.similarity = similarity;
    }

    /**
     * search using match query
     * @param text
     * @return scored document
     */
    public List<DocumentScore> search(String text, int from, int limit) {
        return new ArrayList<>();
    }

    public ResultSet search(Query query, int from, int limit) {
        Scorer scorer = query.scorer(storage, similarity);
        List<DocumentScore> rankedDocs = scorer.scoreDoc();
        if (similarity instanceof NoScoreSimilarity) { // sort by created time or seg id
            rankedDocs = rankedDocs.stream()
                    .sorted(Comparator.comparingLong(Document::getSedId))
                    .collect(Collectors.toList());
        }
        ResultSet rs = new ResultSet(rankedDocs, from, limit);
        rs.getDocumentScores()
                .forEach(documentScore -> documentScore.setDocument(storage.getDoc(documentScore.getDocId())));
        return rs;
    }

}
