package com.devworker.kms.service;

import com.devworker.kms.dto.FtsDto;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.devworker.kms.fts.FTSDao;
import com.devworker.kms.fts.FTSRepo;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class FTSService {
	@Autowired
	FTSRepo repo;
	
	public long getCount() {
		return repo.count();
	}
	
	public void save(FtsDto dto) {
		repo.save(dto.toDao());
	}

	public void delete(long id){
		repo.deleteById(id);
	}
	
	public Page<FTSDao> find(String word) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(multiMatchQuery(word)
						.field("name")
						.field("user")
                        .field("text")
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
				.build();
		return repo.search(searchQuery);
	}
}
