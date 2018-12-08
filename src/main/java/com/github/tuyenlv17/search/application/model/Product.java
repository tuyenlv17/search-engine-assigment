package com.github.tuyenlv17.search.application.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@Data
@Accessors(chain = true)
public class Product {
    String name;
    float score;

    public Product(String name) {
        this.name = name;
    }
}