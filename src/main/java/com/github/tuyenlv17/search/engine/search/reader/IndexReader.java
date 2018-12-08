package com.github.tuyenlv17.search.engine.search.reader;

import com.github.tuyenlv17.search.engine.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tuyenlv17 on 2018-12-08.
 * Abstract class using for read inverted index
 */
public abstract class IndexReader {
    @Autowired Storage storage;
}
