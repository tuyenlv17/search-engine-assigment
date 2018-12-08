package com.github.tuyenlv17.search.engine.document;

import java.util.Objects;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
public class DocTerm extends Term {
    Long docId;
    public DocTerm(String index, String fieldName, Object value, Long docId) {
        super(index, fieldName, value);
        this.docId = docId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DocTerm docTerm = (DocTerm) o;
        return Objects.equals(docId, docTerm.docId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), docId);
    }
}
