package com.github.tuyenlv17.search.application.wio;

import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.application.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
@RestController
public class ProductSearchController {
    public static final Logger LOGGER = LogManager.getLogger(ProductSearchController.class);

    @Autowired ProductRepository productRepository;

    @RequestMapping(path = "/search/bm25", method = RequestMethod.GET)
    public List<Product> searchBM25(@RequestParam(value = "query", defaultValue = "") String query) {
        return productRepository.searchBM25(query);
    }

    @RequestMapping(path = "/search/keyword", method = RequestMethod.GET)
    public List<Product> searchByKeyword(@RequestParam(value = "query", defaultValue = "") String query) {
        return productRepository.searchByKeyword(query);
    }
}
