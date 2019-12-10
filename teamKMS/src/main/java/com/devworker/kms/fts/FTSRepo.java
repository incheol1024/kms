package com.devworker.kms.fts;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FTSRepo
//        extends JpaRepository<FTSDao, Long>
        extends ElasticsearchRepository<FTSDao,Long>
{

}

