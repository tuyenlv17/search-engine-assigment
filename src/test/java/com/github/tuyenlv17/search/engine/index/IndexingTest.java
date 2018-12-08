package com.github.tuyenlv17.search.engine.index;

import com.github.tuyenlv17.search.application.config.search.BM25Config;
import com.github.tuyenlv17.search.application.config.search.IndexConfig;
import com.github.tuyenlv17.search.application.repository.ProductRepository;
import com.github.tuyenlv17.search.application.service.ProductDataLoader;
import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.document.Term;
import com.github.tuyenlv17.search.engine.query.Query;
import com.github.tuyenlv17.search.engine.query.TermQuery;
import com.github.tuyenlv17.search.engine.search.IndexSearcher;
import com.github.tuyenlv17.search.engine.search.similarity.BM25Similarity;
import com.github.tuyenlv17.search.engine.storage.RamStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexingTest {
    public static final Logger LOGGER = LogManager.getLogger(IndexingTest.class);
    @Autowired ProductRepository productRepository;
    @Autowired RamStorage ramStorage;
    @Autowired ProductDataLoader productDataLoader;

    @Autowired IndexConfig indexConfig;
    @Autowired BM25Config bm25Config;

    @Test
    public void indexProducts() {
        productDataLoader
                .loadProducts()
                .forEach(product -> productRepository.add(product));
        ramStorage.debugStats();
    }

    @Test
    public void searchProduct() {
        productDataLoader
                .loadProducts()
                .forEach(product -> productRepository.add(product));
        ramStorage.debugStats();
        IndexSearcher indexSearcher = new IndexSearcher(ramStorage, new BM25Similarity(bm25Config));
        Query query = new TermQuery(new Term(indexConfig.getProductIndex(), "title", "sách"));
        List<DocumentScore> matchedDocument = indexSearcher.search(query, 0, 10).getDocumentScores();
        matchedDocument
                .forEach(documentScore -> LOGGER.debug("matched getDoc {}", documentScore.getDocAsMap().get("title")));
    }

    @Test
    public void testBM25Similarity() {

    }
}
