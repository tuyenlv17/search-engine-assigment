package com.github.tuyenlv17.search.engine.document;

import lombok.Data;

import java.util.Objects;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
@Data
public class DocumentScore extends Document {
    float score;

    public DocumentScore(String id, float score) {
        this.docId = id;
        this.sedId = Long.valueOf(id);
        this.score = score;
    }

    public DocumentScore setDocument(Document document) {
        this.docAsMap = document.getDocAsMap();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Document document = (Document) o;
        return Objects.equals(docId, document.docId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docId);
    }
}
