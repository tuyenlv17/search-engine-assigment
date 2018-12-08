package com.github.tuyenlv17.search.engine.index.writer;

import com.github.tuyenlv17.search.engine.document.Document;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
public abstract class IndexWriter {
    public abstract void index(Document document);

    public abstract void delete(Document document);
}
