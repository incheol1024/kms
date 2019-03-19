package com.devworker.kms.fts;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FTSRepo extends ElasticsearchRepository<FTSDao,Integer>{
	
	List<FTSDao> findByName(String name);
}
