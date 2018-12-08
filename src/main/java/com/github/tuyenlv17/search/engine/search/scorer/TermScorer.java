package com.github.tuyenlv17.search.engine.search.scorer;

import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.document.Term;
import com.github.tuyenlv17.search.engine.index.stat.FieldStats;
import com.github.tuyenlv17.search.engine.index.stat.TermStats;
import com.github.tuyenlv17.search.engine.search.query.TermQuery;
import com.github.tuyenlv17.search.engine.search.similarity.Similarity;
import com.github.tuyenlv17.search.engine.storage.Storage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tuyenlv17 on 2018-12-08.
 * Computing term score using {@link Similarity}
 */
public class TermScorer extends Scorer {
    Term term;
    public TermScorer(TermQuery query, Storage storage, Similarity similarity) {
        super(query, storage, similarity);
        this.term = query.getTerm();
    }

    @Override
    public List<DocumentScore> scoreDoc() {
        return scoreDocSet()
                .stream()
                .sorted((o1, o2) -> Float.compare(o2.getScore(), o1.getScore()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<DocumentScore> scoreDocSet() {
        return storage.getDocScoreByTerm(term)
                .stream()
                .map(documentScore -> caculateScore(documentScore))
                .collect(Collectors.toSet());
    }

    @Override
    public Map<DocumentScore, DocumentScore> scoreDocMap() {
        Map<DocumentScore, DocumentScore> docScoreMap = storage.getDocScoreMapByTerm(term);
        docScoreMap.forEach((documentScore, documentScore2) -> {
            caculateScore(documentScore);
        });
        return docScoreMap;
    }

    protected DocumentScore caculateScore(DocumentScore documentScore) {
        int termFreq = storage.termFreq(term, documentScore.getDocId());
        TermStats termStats = storage.getTermStats(term);
        FieldStats fieldStats = storage.getFieldStats(term.getFieldName());
        int fieldLength = storage.getFieldLength(term.getFieldName(), documentScore.getDocId());
        documentScore.setScore(similarity.score(termFreq, termStats, fieldLength, fieldStats));
        return documentScore;
    }
}
