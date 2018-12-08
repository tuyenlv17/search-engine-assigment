package com.github.tuyenlv17.search.application.service;

import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.application.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tuyenlv17 on 2018-12-08.
 * Load data from products file
 */
@Service
public class ProductDataLoader {
    public static final Logger LOGGER = LogManager.getLogger(ProductDataLoader.class);
    @Autowired ProductRepository productRepository;
    public List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("assignment/product_names-test.txt"));
            int lineCnt = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                lineCnt++;
                if (line.equals("")) {
                    LOGGER.debug("empty line {}", lineCnt);
                    continue;
                }
                Product product = new Product(line);
                products.add(product);
            }
        } catch (Exception e) {
            LOGGER.error("error loading products", e);
        }
        return products;
    }

    @PostConstruct
    public void startLoading() {
        loadProducts().forEach(product -> productRepository.add(product));
    }
}
