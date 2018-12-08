package com.github.tuyenlv17.search.application.repository;

import com.github.tuyenlv17.search.application.config.search.IndexConfig;
import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.document.Field;
import com.github.tuyenlv17.search.engine.index.writer.IndexWriter;
import com.github.tuyenlv17.search.engine.index.writer.RamIndexWriter;
import com.github.tuyenlv17.search.engine.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@Repository
public class ProductRepository {
    @Autowired IndexConfig indexConfig;
    @Autowired RamIndexWriter ramIndexWriter;

    @Autowired IndexWriter indexWriter;
    public void add(Product product) {
        Document document = new Document()
                .setIndex(indexConfig.getProductIndex());
        document.addField(new Field("title", product.getName()));
        document.addField(new Field("title_ar", TextUtils.removeAccent(product.getName())));
        ramIndexWriter.index(document);
    }

    public void delete(Product product) {

    }
}
