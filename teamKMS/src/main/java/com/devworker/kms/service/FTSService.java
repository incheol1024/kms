package com.devworker.kms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.repo.FTSRepo;

@Service
public class FTSService {
	@Autowired
	FTSRepo repo;
	
	public long getCount() {
		return repo.count();
	}
}
