package com.devworker.kms.service.acl;

import com.devworker.kms.repo.acl.AceRepo;
import com.devworker.kms.repo.acl.AclRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AceService {
    @Autowired
    AceRepo aceRepo;
    @Autowired
    AclRepo aclRepo;



}
