package com.devworker.kms.repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.devworker.kms.ftsdao.PostFTSDao;

public interface FTSRepo extends ElasticsearchRepository<PostFTSDao,Integer>{
	
	List<PostFTSDao> findByName(String name);
}
