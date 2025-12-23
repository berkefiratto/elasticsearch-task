package com.elasticsearchtask.repository.elastic;

import com.elasticsearchtask.model.doc.FundDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundSearchRepository extends ElasticsearchRepository<FundDocument, Long> {
}
