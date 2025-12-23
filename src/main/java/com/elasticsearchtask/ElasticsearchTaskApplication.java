package com.elasticsearchtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.elasticsearchtask.repository.jpa")
@EnableElasticsearchRepositories(basePackages = "com.elasticsearchtask.repository.elastic")
public class ElasticsearchTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchTaskApplication.class, args);
    }
}
