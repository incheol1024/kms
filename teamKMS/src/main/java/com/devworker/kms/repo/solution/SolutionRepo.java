package com.devworker.kms.repo.solution;

import com.devworker.kms.entity.solution.SolutionDao;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SolutionRepo extends PagingAndSortingRepository<SolutionDao, Long> {
}
