package com.devworker.kms.service.acl;

import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.acl.AceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AceService {
    @Autowired
    AceRepo aceRepo;
    @Autowired
    AclService aclService;

    public String addAce(AceDto dto) {
        aclService.getAcl(dto.getAclId());
        return aceRepo.save(dto.getDao()).getAceId();
    }

    public void updateAce(AceDto dto) {
        aclService.getAcl(dto.getAclId());
        aceRepo.save(dto.getDao());
    }

    public void deleteAce(AceDto dto) {
        aceRepo.delete(dto.getDao());
    }

    public Page<AceDto> getAceList(Pageable pageable) {
        return aceRepo.findAll(pageable).map(aceDao -> aceDao.getDto());
    }

    public Page<AceDto> getAceListByAclId(String aclId, Pageable pageable) {
        return aceRepo.findByAclId(aclId, pageable).map(aceDao -> aceDao.getDto());
    }

    public List<AceDto> getAceByAceId(String aceId){
        return aceRepo.findByAceId(aceId).stream().map(aceDao -> aceDao.getDto()).collect(Collectors.toList());
    }
}
