package com.github.tuyenlv17.search.application.mapper;

import com.github.tuyenlv17.search.application.config.search.IndexConfig;
import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.document.Field;
import com.github.tuyenlv17.search.engine.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
@Service
public class ProductMapper {
    @Autowired IndexConfig indexConfig;
    public Document toDocument(Product product) {
        Document document = new Document()
                .setIndex(indexConfig.getProductIndex());
        document.addField(new Field(indexConfig.getProductIndex(), "title", product.getName()));
        document.addField(new Field(indexConfig.getProductIndex(), "title_ar", TextUtils.removeAccent(product.getName())));
        return document;
    }
}
