package com.github.tuyenlv17.search.engine.storage;

import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.document.Field;
import com.github.tuyenlv17.search.engine.document.Term;
import com.github.tuyenlv17.search.engine.index.stat.TermStats;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
public abstract class AbstractStorage {
    /**
     * Get incremented doc id
     * @return doc id
     */
    public abstract String getNewDocId();
    public abstract long docCount();
    public abstract void addDocSource(Document document);
    public abstract void addDocToInvertedIndex(Term term, Document document);
    public abstract void removeDocFromInvertedIndex(Term term, Document document);
    public abstract void removeDocumentSource(Document document);
    public abstract Document doc(String docId);
    public abstract int docFreq(Term term);
    public abstract TermStats termStats(Term term);

    /**
     * Update doc freq for current term based on status is inserting (1) or deleting (-1)
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
    public abstract void updateFieldStats(Field field, int status);
    public abstract List<String> getDocIdsByTerm(Term term);

    /**
     * Index document with analyzed fields in underline storage
     * @param document
     */
    public void indexDocument(Document document) {
        String docId = getNewDocId();
        indexDocument(document, docId);
    }

    /**
     * Index document with analyzed fields in underline storage
     * @param document
     */
    public void indexDocument(Document document, String docId) {
        document.setDocId(docId);
        Document existedDoc = doc(docId);
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
}
