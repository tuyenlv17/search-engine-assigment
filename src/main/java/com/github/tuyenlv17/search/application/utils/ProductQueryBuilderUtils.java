package com.github.tuyenlv17.search.application.utils;

import com.github.tuyenlv17.search.application.mapper.ProductMapper;
import com.github.tuyenlv17.search.application.model.Product;
import com.github.tuyenlv17.search.engine.document.Document;
import com.github.tuyenlv17.search.engine.search.query.BooleanQuery;
import com.github.tuyenlv17.search.engine.search.query.DisMaxQuery;
import com.github.tuyenlv17.search.engine.search.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.github.tuyenlv17.search.application.contants.FieldConstants.TITLE;
import static com.github.tuyenlv17.search.application.contants.FieldConstants.TITLE_AR;
import static com.github.tuyenlv17.search.engine.search.query.parser.QueryParserUtils.toBooleanQuery;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
@Service
public class ProductQueryBuilderUtils {
    @Autowired ProductMapper productMapper;
    /**
     * Create search product by name, boost accent name more than non-accent name
     * @param product
     * @return Search product by name {@link Query}
     */
    public Query searchProductByName(Product product) {
        Document document = productMapper.toDocument(product);
        BooleanQuery titleQuery = toBooleanQuery(document.getFieldMap().get(TITLE));
        BooleanQuery titleArQuery = toBooleanQuery(document.getFieldMap().get(TITLE_AR));
        DisMaxQuery disMaxQuery = new DisMaxQuery()
                .add(titleQuery.setBoost(1.1f))
                .add(titleArQuery);
        return disMaxQuery;
    }
}
