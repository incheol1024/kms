package com.devworker.kms.repo.solution;

import com.devworker.kms.entity.solution.SolutionPatchDao;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SolutionPatchRepo  extends PagingAndSortingRepository<SolutionPatchDao, Long> {
}
