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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tuyenlv17 on 2018-12-04.
 * Using ram for storing inverted index and stats info, one application only run one instant of {@link RamStorage}
 */
@Service
public class RamStorage extends AbstractStorage {
    public static final Logger LOGGER = LogManager.getLogger(RamStorage.class);
    long incrId;
    Map<Term, Set<String>> invertedIndex;
    Map<Term, Map<String, Integer>> termFreqMap;
    Map<Term, TermStats> termStatsMap;
    Map<String, FieldStats> fieldStatsMap;
    Map<String, Document> documentMap;

    @PostConstruct
    public void setUpStorage() {
        incrId = 0;
        invertedIndex = new HashMap<>();
        termFreqMap = new HashMap<>();
        termStatsMap = new HashMap<>();
        fieldStatsMap = new HashMap<>();
        documentMap = new HashMap<>();
    }

    @Override
    public String getNewDocId() {
        //TODO check concurrency
        return String.valueOf(++incrId);
    }

    @Override
    public long docCount() {
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
    public Document doc(String docId) {
        return documentMap.get(docId);
    }

    @Override
    public int docFreq(Term term) {
        TermStats termStat = termStatsMap.get(term);
        if (termStat == null) {
            return 0;
        }
        return termStat.getDocFreq();
    }

    @Override
    public TermStats termStats(Term term) {
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
    public void updateFieldStats(Field field, int status) {
        FieldStats fieldStats = fieldStatsMap.get(field.getName());
        if (fieldStats == null) {
            fieldStats = new FieldStats(0, 0);
        }
        fieldStats.setDocCount(fieldStats.getDocCount() + 1 * status);
        fieldStats.setTotalTermFreq(fieldStats.getTotalTermFreq() + field.getAnalyzedTokens().size() * status);
    }

    @Override
    public List<String> getDocIdsByTerm(Term term) {
        return invertedIndex.get(term).stream().collect(Collectors.toList());
    }

    public void debugStats() {
        LOGGER.debug("storage stats {}", incrId);
    }
}
