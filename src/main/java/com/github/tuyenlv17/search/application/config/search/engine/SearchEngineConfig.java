package com.github.tuyenlv17.search.application.config.search.engine;

import com.github.tuyenlv17.search.application.config.search.BM25Config;
import com.github.tuyenlv17.search.engine.search.IndexSearcher;
import com.github.tuyenlv17.search.engine.search.similarity.BM25Similarity;
import com.github.tuyenlv17.search.engine.search.similarity.NoScoreSimilarity;
import com.github.tuyenlv17.search.engine.storage.RamStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
@Component
public class SearchEngineConfig {
    @Autowired RamStorage ramStorage;
    @Autowired BM25Config bm25Config;
    @Bean
    public IndexSearcher bm25IndexSearcher() {
        IndexSearcher indexSearcher = new IndexSearcher(ramStorage, new BM25Similarity(bm25Config));
        return indexSearcher;
    }

    @Bean
    public IndexSearcher keywordIndexSearcher() {
        IndexSearcher indexSearcher = new IndexSearcher(ramStorage, new NoScoreSimilarity());
        return indexSearcher;
    }
}
