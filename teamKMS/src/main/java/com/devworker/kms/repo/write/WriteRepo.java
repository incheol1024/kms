package com.devworker.kms.repo.write;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devworker.kms.entity.solution.SolutionDao;

public interface WriteRepo extends JpaRepository<SolutionDao, Long>{

}
