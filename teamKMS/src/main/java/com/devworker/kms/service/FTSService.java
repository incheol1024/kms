package com.devworker.kms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.ftsdto.PostFTSDto;
import com.devworker.kms.repo.FTSRepo;

@Service
public class FTSService {
	@Autowired
	FTSRepo repo;
	
	public long getCount() {
		return repo.count();
	}
	
	public void save(PostFTSDto dto) {
		repo.save(dto);
	}
	
	public List<PostFTSDto> findbyuser(String name) {
		return repo.findByName(name);
	}
}
