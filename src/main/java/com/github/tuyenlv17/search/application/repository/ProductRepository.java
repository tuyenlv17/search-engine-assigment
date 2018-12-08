package com.github.tuyenlv17.search.application.repository;

import com.github.tuyenlv17.search.application.config.search.IndexConfig;
import com.github.tuyenlv17.search.application.mapper.ProductMapper;
import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.application.utils.ProductQueryBuilderUtils;
import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.index.writer.RamIndexWriter;
import com.github.tuyenlv17.search.engine.search.IndexSearcher;
import com.github.tuyenlv17.search.engine.search.query.Query;
import com.github.tuyenlv17.search.engine.search.result.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@Repository
public class ProductRepository {
    @Autowired IndexConfig indexConfig;
    @Autowired RamIndexWriter ramIndexWriter;
    @Autowired ProductQueryBuilderUtils queryBuilderUtils;
    @Autowired IndexSearcher bm25IndexSearcher;
    @Autowired IndexSearcher keywordIndexSearcher;

    @Autowired ProductMapper productMapper;

    public void add(Product product) {
        Document document = productMapper.toDocument(product);
        ramIndexWriter.index(document);
    }

    public void delete(Product product) {

    }

    public List<Product> searchBM25(String queryStr) {
        Product product = new Product(queryStr);
        Query query= queryBuilderUtils.searchProductByName(product);
        ResultSet resultSet = bm25IndexSearcher.search(query, 0, 10);
        return resultSet.getDocumentScores()
                .stream()
                .map(documentScore -> productMapper.toProduct(documentScore))
                .collect(Collectors.toList());
    }

    public List<Product> searchByKeyword(String queryStr) {
        Product product = new Product(queryStr);
        Query query= queryBuilderUtils.searchProductByName(product);
        ResultSet resultSet = keywordIndexSearcher.search(query, 0, 10);
        return resultSet.getDocumentScores()
                .stream()
                .map(documentScore -> productMapper.toProduct(documentScore))
                .collect(Collectors.toList());
    }
}
