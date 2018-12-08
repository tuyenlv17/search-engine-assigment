package com.github.tuyenlv17.search.engine.index.writer;

import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.storage.RamStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@Service
public class RamIndexWriter extends IndexWriter {
    @Autowired RamStorage ramStorage;

    @Override
    public void index(Document document) {
        ramStorage.indexDocument(document);
    }

    @Override
    public void delete(Document document) {
        ramStorage.deleteDocument(document);
    }
}
