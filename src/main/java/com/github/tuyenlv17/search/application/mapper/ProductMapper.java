package com.github.tuyenlv17.search.application.mapper;

import com.github.tuyenlv17.search.application.config.search.IndexConfig;
import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.document.DocumentScore;
import com.github.tuyenlv17.search.engine.document.Field;
import com.github.tuyenlv17.search.engine.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.github.tuyenlv17.search.application.contants.FieldConstants.TITLE;
import static com.github.tuyenlv17.search.application.contants.FieldConstants.TITLE_AR;
/**
 * Created by tuyenlv17 on 2018-12-08.
 */
@Service
public class ProductMapper {
    @Autowired IndexConfig indexConfig;
    public Document toDocument(Product product) {
        Document document = new Document()
                .setIndex(indexConfig.getProductIndex());
        document.addField(new Field(indexConfig.getProductIndex(), TITLE, product.getName()));
        document.addField(new Field(indexConfig.getProductIndex(), TITLE_AR, TextUtils.removeAccent(product.getName())));
        return document;
    }

    public Product toProduct(DocumentScore document) {
        Product product = new Product(document.getDocAsMap().get(TITLE));
        product.setScore(document.getScore());
        return product;
    }
}
