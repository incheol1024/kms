package com.devworker.kms.service.acl;

import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.repo.acl.AceRepo;
import com.devworker.kms.repo.acl.AclRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AceService {
    @Autowired
    AceRepo aceRepo;
    @Autowired
    AclRepo aclRepo;


    public String addAce(AceDto dto) {
        return null;
    }

    public void updateAce(AceDto dto) {
    }

    public void deleteAce(String aclId, String aceId) {
    }

    public Page<AceDto> getAceList(Pageable pageable) {
        return null;
    }


    public Page<AceDto> getAceListByAclId(String aclId, Pageable pageable) {
        return null;
    }
}
