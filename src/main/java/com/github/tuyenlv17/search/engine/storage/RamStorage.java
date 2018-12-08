package com.github.tuyenlv17.search.engine.storage;

import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.document.Field;
import com.github.tuyenlv17.search.engine.document.Term;
import com.github.tuyenlv17.search.engine.index.stat.FieldStats;
import com.github.tuyenlv17.search.engine.index.stat.TermStats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.github.tuyenlv17.search.engine.constants.UpdateStatusContants.INSERT;

/**
 * Created by tuyenlv17 on 2018-12-04.
 * Using ram for storing inverted index and stats info, one application only run one instant of {@link Storage}
 */
@Service
public class RamStorage extends Storage {
    public static final Logger LOGGER = LogManager.getLogger(Storage.class);
    long incrId;
    Map<Term, Set<String>> invertedIndex;
    Map<Term, Map<String, Integer>> termFreqMap;
    Map<Term, TermStats> termStatsMap;
    Map<String, FieldStats> fieldStatsMap;
    Map<String, Map<String, Integer>> fieldLengthMap;
    Map<String, Document> documentMap;

    @PostConstruct
    public void setUpStorage() {
        incrId = 0;
        invertedIndex = new HashMap<>();
        termFreqMap = new HashMap<>();
        termStatsMap = new HashMap<>();
        fieldStatsMap = new HashMap<>();
        documentMap = new HashMap<>();
        fieldLengthMap = new HashMap<>();
    }

    @Override
    public String getNewDocId() {
        //TODO check concurrency
        return String.valueOf(++incrId);
    }

    @Override
    public long getDocCount() {
        return documentMap.size();
    }

    @Override
    public void addDocSource(Document document) {
        documentMap.put(document.getDocId(), document);
    }

    @Override
    public void addDocToInvertedIndex(Term term, Document document) {
        Set<String> docs = invertedIndex.get(term);
        if (docs == null) {
            docs = new HashSet<>();
            invertedIndex.put(term, docs);
        }
        docs.add(document.getDocId());
    }

    @Override
    public void removeDocFromInvertedIndex(Term term, Document document) {
        Set<String> docs = invertedIndex.get(term);
        if (docs != null) {
            docs.remove(document.getDocId());
        }
    }

    @Override
    public void removeDocumentSource(Document document) {
        documentMap.remove(document.getDocId());
    }

    @Override
    public Document getDoc(String docId) {
        return documentMap.get(docId);
    }

    @Override
    public int getDocFreq(Term term) {
        TermStats termStat = termStatsMap.get(term);
        if (termStat == null) {
            return 0;
        }
        return termStat.getDocFreq();
    }

    @Override
    public TermStats getTermStats(Term term) {
        TermStats termStat = termStatsMap.get(term);
        return termStat;
    }

    @Override
    public void updateDocFreq(Term term, int status) {
        TermStats termStat = termStatsMap.get(term);
        if (termStat == null) {
            termStat = new TermStats()
                    .setDocFreq(0);
            termStatsMap.put(term, termStat);
        }
        termStat.setDocFreq(termStat.getDocFreq() + 1 * status);
    }

    @Override
    public int termFreq(Term term, String docId) {
        Map<String, Integer> stringIntegerMap = termFreqMap.get(term);
        if (stringIntegerMap == null) {
            return 0;
        }
        return stringIntegerMap.getOrDefault(docId, 0);
    }

    @Override
    public void updateTermFreq(Term docTerm, String docId, int termFreq) {
        Map<String, Integer> termFreqInDoc = termFreqMap.get(docTerm);
        if (termFreqInDoc == null) {
            termFreqInDoc = new HashMap<>();
            termFreqMap.put(docTerm, termFreqInDoc);
        }
        termFreqInDoc.put(docId, termFreq);
    }

    @Override
    public float avgFieldLen(String fieldName) {
        FieldStats fieldStats = fieldStatsMap.get(fieldName);
        if (fieldStats == null) {
            //TODO recheck scoring rules
            return 0;
        }
        return fieldStats.getAvgFieldLength();
    }

    @Override
    public FieldStats getFieldStats(String fieldName) {
        FieldStats fieldStats = fieldStatsMap.get(fieldName);
        if (fieldStats == null) {
            return new FieldStats(documentMap.size(), documentMap.size(), 0);
        }
        return fieldStats;
    }

    @Override
    public int getFieldLength(String fieldName, String docID) {
        Map<String, Integer> fieldLengths = fieldLengthMap.get(fieldName);
        if (fieldLengths == null) {
            return 0;
        }
        return fieldLengths.getOrDefault(docID, 0);
    }

    @Override
    public void updateFieldLength(String fieldName, String docID, int fieldLength, int status) {
        Map<String, Integer> fieldLengths = fieldLengthMap.get(fieldName);
        if (fieldLengths == null) {
            fieldLengths = new HashMap<>();
            fieldLengthMap.put(fieldName, fieldLengths);
        }
        if (status == INSERT) {
            fieldLengths.put(docID, fieldLength);
        } else {
            fieldLengths.remove(docID);
        }
    }

    @Override
    public void updateFieldStats(Field field, int status) {
        FieldStats fieldStats = fieldStatsMap.get(field.getName());
        if (fieldStats == null) {
            fieldStats = new FieldStats(0, 0);
            fieldStatsMap.put(field.getName(), fieldStats);
        }
        fieldStats.setDocCount(fieldStats.getDocCount() + 1 * status);
        fieldStats.setMaxDocCount(documentMap.size());
        fieldStats.setTotalTermFreq(fieldStats.getTotalTermFreq() + field.getAnalyzedTokens().size() * status);
    }

    @Override
    public Set<String> getDocIdsByTerm(Term term) {
        return invertedIndex.get(term);
    }

    public void debugStats() {
        LOGGER.debug("storage stats {}", incrId);
    }
}
