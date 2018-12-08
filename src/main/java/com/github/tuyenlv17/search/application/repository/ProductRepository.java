package com.github.tuyenlv17.search.application.repository;

import com.github.tuyenlv17.search.application.config.search.IndexConfig;
import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.document.Field;
import com.github.tuyenlv17.search.engine.index.writer.RamIndexWriter;
import com.github.tuyenlv17.search.engine.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@Repository
public class ProductRepository {
    @Autowired IndexConfig indexConfig;
    @Autowired RamIndexWriter ramIndexWriter;

    public void add(Product product) {
        Document document = new Document()
                .setIndex(indexConfig.getProductIndex());
        document.addField(new Field(indexConfig.getProductIndex(), "title", product.getName()));
        document.addField(new Field(indexConfig.getProductIndex(), "title_ar", TextUtils.removeAccent(product.getName())));
        ramIndexWriter.index(document);
    }

    public void delete(Product product) {

    }
}
