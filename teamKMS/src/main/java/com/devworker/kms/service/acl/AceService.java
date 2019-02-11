package com.devworker.kms.service.acl;

import com.devworker.kms.entity.acl.AceDao;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.repo.acl.AceRepo;

import java.util.ArrayList;
import java.util.List;

import com.devworker.kms.util.AclUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class AceService {
    private final
    AceRepo aceRepo;
    private final
    AclService aclService;

    @Autowired
    public AceService(AceRepo aceRepo, AclService aclService) {
        this.aceRepo = aceRepo;
        this.aclService = aclService;
    }

    public String addAce(AceDto dto) {
        AclUtil.checkPermission(PermissionType.CREATEPERMISSION);
        aclService.getAcl(dto.getAclId());
        return aceRepo.save(dto.getDao()).getAceId();
    }

    public void updateAce(AceDto dto) {
        AclUtil.checkPermission(PermissionType.MODIFYPERMISSION);
        aclService.getAcl(dto.getAclId());
        aceRepo.save(dto.getDao());
    }

    public void deleteAce(AceDto dto) {
        AclUtil.checkPermission(PermissionType.DELETEPERMISSION);
        aceRepo.delete(dto.getDao());
    }

    public Page<AceDto> getAceList(Pageable pageable) {
        return aceRepo.findAll(pageable).map(AceDao::getDto);
    }

    public Page<AceDto> getAceListByAclId(String aclId, Pageable pageable) {
        return aceRepo.findByAclId(aclId, pageable).map(AceDao::getDto);
    }

    public List<AceDto> getAceByAceId(String aceId){
        List<AceDto> list = new ArrayList<>();
        for (AceDao aceDao : aceRepo.findByAceId(aceId)) {
            AceDto dto = aceDao.getDto();
            list.add(dto);
        }
        return list;
    }
}
