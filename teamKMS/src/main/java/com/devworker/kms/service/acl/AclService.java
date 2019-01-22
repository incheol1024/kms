package com.devworker.kms.service.acl;

import com.devworker.kms.repo.acl.AclRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AclService {
    @Autowired
    AclRepo aclRepo;


}
