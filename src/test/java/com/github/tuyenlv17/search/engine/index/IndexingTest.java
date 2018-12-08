package com.github.tuyenlv17.search.engine.index;

import com.github.tuyenlv17.search.application.config.search.BM25Config;
import com.github.tuyenlv17.search.application.config.search.IndexConfig;
import com.github.tuyenlv17.search.application.mapper.ProductMapper;
import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.application.repository.ProductRepository;
import com.github.tuyenlv17.search.application.service.ProductDataLoader;
import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.document.Term;
import com.github.tuyenlv17.search.engine.search.IndexSearcher;
import com.github.tuyenlv17.search.engine.search.query.BooleanQuery;
import com.github.tuyenlv17.search.engine.search.query.DisMaxQuery;
import com.github.tuyenlv17.search.engine.search.query.Query;
import com.github.tuyenlv17.search.engine.search.query.TermQuery;
import com.github.tuyenlv17.search.engine.search.query.parser.QueryParserUtils;
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

    @Autowired ProductMapper productMapper;

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
        loadData();
        IndexSearcher indexSearcher = new IndexSearcher(ramStorage, new BM25Similarity(bm25Config));
        Query query = new TermQuery(new Term(indexConfig.getProductIndex(), "title", "sách"));
        List<DocumentScore> matchedDocument = indexSearcher.search(query, 0, 10).getDocumentScores();
        matchedDocument
                .forEach(documentScore -> LOGGER.debug("matched getDoc {}", documentScore.getDocAsMap().get("title")));
    }

    @Test
    public void testBM25Similarity() {

    }

    @Test
    public void testBooleanQuery() {
        loadData();
        Product product = new Product("loa bluetooth");
        Document document = productMapper.toDocument(product);
        BooleanQuery booleanQuery = QueryParserUtils.toBooleanQuery(document.getFields().get(0));
        IndexSearcher indexSearcher = new IndexSearcher(ramStorage, new BM25Similarity(bm25Config));
        List<DocumentScore> matchedDocument = indexSearcher.search(booleanQuery, 0, 10).getDocumentScores();
        matchedDocument
                .forEach(documentScore -> LOGGER.debug("matched getDoc {}", documentScore.getDocAsMap().get("title")));
    }

    @Test
    public void testDisMaxQuery() {
        loadData();
        Product product = new Product("giấy nhắc nhở");
        Document document = productMapper.toDocument(product);
        BooleanQuery titleQuery = QueryParserUtils.toBooleanQuery(document.getFieldMap().get("title"));
        BooleanQuery titleArQuery = QueryParserUtils.toBooleanQuery(document.getFieldMap().get("title"));
        DisMaxQuery disMaxQuery = new DisMaxQuery()
                .add(titleQuery.setBoost(1.2f))
                .add(titleArQuery);
        IndexSearcher indexSearcher = new IndexSearcher(ramStorage, new BM25Similarity(bm25Config));
        List<DocumentScore> matchedDocument = indexSearcher.search(disMaxQuery, 0, 10).getDocumentScores();
        matchedDocument
                .forEach(documentScore -> LOGGER.debug("matched getDoc {}", documentScore.getDocAsMap().get("title")));
    }


    @Test
    public void testBM25Search() {
        loadData();
        productRepository.searchBM25("giấy nhắc nhở")
                .forEach(product -> LOGGER.debug("{}: {}", product.getName(), product.getScore()));
    }

    @Test
    public void testKeywordSearch() {
        loadData();
        productRepository.searchByKeyword("giấy nhắc nhở")
                .forEach(product -> LOGGER.debug("{}: {}", product.getName(), product.getScore()));
    }

    protected void loadData() {
        productDataLoader
                .loadProducts()
                .forEach(product -> productRepository.add(product));
        ramStorage.debugStats();
    }
}
