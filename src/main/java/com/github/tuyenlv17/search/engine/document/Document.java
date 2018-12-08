package com.github.tuyenlv17.search.engine.document;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tuyenlv17 on 2018-12-04.
 */
@Data
@Accessors(chain = true)
public class Document {
    String docId;
    String index;
    List<Field> fields;
    Map<String, Integer> fieldLength;

    public Document addField(Field field) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(field.setIndex(index));
        return this;
    }
}
