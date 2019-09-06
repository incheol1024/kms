package com.devworker.kms.fts;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FTSRepo extends ElasticsearchRepository<FTSDao,Long>{

}
