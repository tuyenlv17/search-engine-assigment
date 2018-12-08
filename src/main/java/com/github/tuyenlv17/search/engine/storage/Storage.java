package com.github.tuyenlv17.search.engine.storage;

import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.document.Field;
import com.github.tuyenlv17.search.engine.document.Term;
import com.github.tuyenlv17.search.engine.index.stat.FieldStats;
import com.github.tuyenlv17.search.engine.index.stat.TermStats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.stream.Collectors;
/**
 * Created by tuyenlv17 on 2018-12-05.
 */
public abstract class Storage {
    public static final Logger LOGGER = LogManager.getLogger(Storage.class);
    /**
     * Get incremented getDoc id
     * @return getDoc id
     */
    public abstract String getNewDocId();
    public abstract long getDocCount();
    public abstract void addDocSource(Document document);
    public abstract void addDocToInvertedIndex(Term term, Document document);
    public abstract void removeDocFromInvertedIndex(Term term, Document document);
    public abstract void removeDocumentSource(Document document);
    public abstract Document getDoc(String docId);
    public abstract int getDocFreq(Term term);
    public abstract TermStats getTermStats(Term term);
    public abstract FieldStats getFieldStats(String fieldName);
    public abstract int getFieldLength(String fieldName, String docID);
    public abstract void updateFieldLength(String fieldName, String docID, int fieldLength, int status);
    /**
     * Update getDoc freq for current term based on status is inserting (1) or deleting (-1)
     * @param term
     * @param status
     */
    public abstract void updateDocFreq(Term term, int status);
    public abstract int termFreq(Term term, String docId);

    /**
     * update term frequence for docID
     * @param docTerm
     * @param docId
     * @param termFreq
     */
    public abstract void updateTermFreq(Term docTerm, String docId, int termFreq);
    public abstract float avgFieldLen(String fieldName);

    /**
     * Update field stats
     * @param field
     * @param status
     */
    public abstract void updateFieldStats(Field field, int status);
    public abstract Set<String> getDocIdsByTerm(Term term);

    /**
     * Get {@link DocumentScore} based on term
     * @param term
     * @return DocumentScore
     */
    public Set<DocumentScore> getDocScoreByTerm(Term term) {
        return getDocIdsByTerm(term)
                .stream()
                .map(s -> new DocumentScore(s, 0))
                .map(s -> {
                    LOGGER.debug("doc {}", getDoc(s.getDocId()).getDocAsMap());
                    s.setDocAsMap(getDoc(s.getDocId()).getDocAsMap());
                    return s;
                })
                .collect(Collectors.toSet());
    }

    /**
     * Index document with analyzed fields in underline storage
     * @param document
     */
    public void indexDocument(Document document) {
        String docId = getNewDocId();
        document.setSedId(Long.valueOf(docId));
        indexDocument(document, docId);
    }

    /**
     * Index document with analyzed fields in underline storage
     * @param document
     */
    public void indexDocument(Document document, String docId) {
        document.setDocId(docId);
        Document existedDoc = getDoc(docId);
        if (existedDoc != null) {
            deleteDocument(document);
        }
        addDocSource(document);
        updateIndex(document, 1);
    }

    public void deleteDocument(Document document) {
        removeDocumentSource(document);
        updateIndex(document, -1);
    }

    /**
     * update inverted index and other stats with status: 1 for insert, -1 for delete
     * @param document
     * @param status
     */
    protected void updateIndex(Document document, int status) {
        document.getFields()
                .forEach(field -> {
                    updateFieldStats(field, status);
                    updateFieldLength(field.getName(), document.getDocId(), field.getAnalyzedTokens().size(), status);
                    field.getAnalyzedTokens()
                            .stream()
                            .collect(Collectors.groupingBy(term -> term, Collectors.counting()))
                            .forEach((term, cnt) -> {
                                if (status == 1) {
                                    addDocToInvertedIndex(term, document);
                                } else {
                                    removeDocFromInvertedIndex(term, document);
                                }
                                updateDocFreq(term, status);
                                updateTermFreq(term, document.getDocId(), cnt.intValue());
                            });
                });
    }

    /**
     * Debug inverted index on underline storage
     */
    public abstract void debugStats();
}

