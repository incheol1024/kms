package com.devworker.kms.repo.site;

import com.devworker.kms.entity.site.SiteDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepo extends PagingAndSortingRepository<SiteDao, Integer> {

    @Query ("select t from SiteDao t where t.menuId = :menuId")
    List<SiteDao> getAllSite(@Param("menuId") int menuId);
}
