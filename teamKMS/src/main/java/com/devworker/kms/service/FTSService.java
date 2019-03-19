package com.devworker.kms.service;

import java.util.List;

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
	
	public void save(FTSDao dto) {
		repo.save(dto);
	}
	
	public List<FTSDao> findbyuser(String name) {
		return repo.findByName(name);
	}
}
