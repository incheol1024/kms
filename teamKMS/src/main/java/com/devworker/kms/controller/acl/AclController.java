package com.devworker.kms.controller.acl;

import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.service.acl.AclService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/acl")
public class AclController {
    @Autowired
    AclService aclService;

    @ApiOperation (value = "신규 권한 그룹 추가")
    @PutMapping
    public String addAcl(@Valid @RequestBody AclDto dto) {
        return aclService.addAcl(dto);
    }

    @ApiOperation (value = "권한 정보 변경")
    @PostMapping
    public void updateAcl(@Valid @RequestBody AclDto dto){
        aclService.updateAcl(dto);
    }

    @ApiOperation (value = "권한 정보 삭제")
    @DeleteMapping("/{aclId}")
    public void deleteAcl(@PathVariable String aclId) {
        aclService.deleteAcl(aclId);
    }

    @ApiOperation (value = "한개의 권한 정보 가져오기")
    @GetMapping("/{aclId}")
    public AclDto getAcl(@PathVariable String aclId) {
        return aclService.getAcl(aclId);
    }

    @ApiOperation (value = "권한 정보 목록 가져오기")
    @GetMapping
    public Page<AclDto> getAclList(Pageable pageable) {
        return aclService.getAclPage(pageable);
    }
}
