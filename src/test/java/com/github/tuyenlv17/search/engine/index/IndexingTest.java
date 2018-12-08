package com.github.tuyenlv17.search.engine.index;

import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.application.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexingTest {
    @Autowired ProductRepository productRepository;

    @Test
    public void indexProduct() {
        Product product = new Product("Sinh ra để chạy");
        productRepository.add(product);
    }
}
