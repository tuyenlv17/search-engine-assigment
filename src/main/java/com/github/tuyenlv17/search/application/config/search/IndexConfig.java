package com.github.tuyenlv17.search.application.config.search;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tuyenlv17 on 2018-12-05.
 */
@Data
@Configuration
@ConfigurationProperties("tiki.search.index")
public class IndexConfig {
    String productIndex;
}
