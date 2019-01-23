package com.devworker.kms.service.acl;

import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.repo.acl.AclRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AclService {
    @Autowired
    AclRepo aclRepo;


    public String addAcl(AclDto dto) {
        return null;
    }

    public void deleteAcl(String aclId) {
    }

    public AclDto getAcl(String aclId) {
        return null;
    }

    public Page<AclDto> getAclPage(Pageable pageable) {
        return null;
    }

    public void updateAcl(AclDto dto) {
    }
}
