package com.devworker.kms.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.devworker.kms.ftsdto.PostFTSDto;

public interface FTSRepo extends ElasticsearchRepository<PostFTSDto,Integer>{

}
