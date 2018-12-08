package com.github.tuyenlv17.search.engine.document;

import lombok.Data;

import java.util.Objects;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@Data
public class Term {
    String index;
    String fieldName;
    Object value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Term term = (Term) o;
        return Objects.equals(index, term.index) &&
                Objects.equals(fieldName, term.fieldName) &&
                Objects.equals(value, term.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), index, fieldName, value);
    }

    public Term(String index, String fieldName, Object value) {

        this.index = index;
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Term{" +
                "fieldName='" + fieldName + '\'' +
                ", value=" + value +
                '}';
    }
}
