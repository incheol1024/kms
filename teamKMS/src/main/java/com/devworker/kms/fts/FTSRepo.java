package com.devworker.kms.fts;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FTSRepo extends ElasticsearchRepository<FTSDao,Long>{
	
	List<FTSDao> findByName(String name);
}
