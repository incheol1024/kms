package com.devworker.kms.service.acl;

import com.devworker.kms.entity.acl.AclDao;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.acl.AclRepo;
import com.devworker.kms.util.AclUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AclService {
    private final
    AclRepo aclRepo;

    @Autowired
    public AclService(AclRepo aclRepo) {
        this.aclRepo = aclRepo;
    }


    public String addAcl(AclDto dto) {
        AclUtil.checkPermission(PermissionType.CREATEPERMISSION);
        aclRepo.save(dto.getDao());
        return dto.getAclId();
    }

    public void deleteAcl(String aclId) {
        AclUtil.checkPermission(PermissionType.DELETEPERMISSION);
        aclRepo.findById(aclId).orElseThrow(() -> new NotExistException("Acl Not Exist"));
        aclRepo.deleteById(aclId);
    }

    public AclDto getAcl(String aclId) {
        return aclRepo.findById(aclId).orElseThrow(() -> new NotExistException("Acl Not Exist")).getDto();
    }

    public Page<AclDto> getAclPage(Pageable pageable) {
        return aclRepo.findAll(pageable).map(AclDao::getDto);
    }

    public void updateAcl(AclDto dto) {
        AclUtil.checkPermission(PermissionType.MODIFYPERMISSION);
        aclRepo.findById(dto.getAclId()).orElseThrow(() -> new NotExistException("Acl Not Exist"));
        aclRepo.save(dto.getDao());
    }
}
