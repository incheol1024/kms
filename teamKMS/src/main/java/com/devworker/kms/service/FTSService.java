package com.devworker.kms.service;

import java.util.List;
import java.util.stream.Collectors;

import com.devworker.kms.dto.FtsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.fts.FTSDao;
import com.devworker.kms.fts.FTSRepo;

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
	
	public List<FtsDto> findByUser(String name) {
		return repo.findByName(name).stream().map(FTSDao::toDto).collect(Collectors.toList());
	}
}
