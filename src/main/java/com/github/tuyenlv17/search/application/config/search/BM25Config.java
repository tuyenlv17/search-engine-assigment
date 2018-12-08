package com.github.tuyenlv17.search.application.config.search;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tuyenlv17 on 2018-12-08.
 */
@Data
@Configuration
@ConfigurationProperties("tiki.search.relevant.similarity.bm25")
public class BM25Config {
    float k1;
    float b;
    String productIndex;
}
