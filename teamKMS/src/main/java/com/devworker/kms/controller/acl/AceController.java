package com.devworker.kms.controller.acl;

import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.service.acl.AceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ace")
public class AceController {
    private final
    AceService aceService;

    @Autowired
    public AceController(AceService aceService) {
        this.aceService = aceService;
    }

    @ApiOperation (value = "ACL에 신규 유저,그룹 할당하기")
    @PutMapping
    public String addAce(@Valid @RequestBody AceDto dto) {
        return aceService.addAce(dto);
    }

    @ApiOperation (value = "ACL에 해당하는 유저 그룹 정보 변경하기")
    @PostMapping
    public void updateAce(@Valid  @RequestBody AceDto dto) {
        aceService.updateAce(dto);
    }

    @ApiOperation (value = "ACL에서 그룹 또는 유저 삭제하기")
    @DeleteMapping("/{aclId}/{aceId}")
    public void deleteAce(@PathVariable String aclId, @PathVariable String aceId) {
        AceDto dto = new AceDto();
        dto.setAclId(aclId);
        dto.setAceId(aceId);
        aceService.deleteAce(dto);
    }

    @ApiOperation (value = "ACL이 할당된 전체 유저,그룹 가져오기")
    @GetMapping
    public Page<AceDto> getAceList(Pageable pageable) {
        return aceService.getAceList(pageable);
    }

    @ApiOperation (value = "특정 ACL에 해당하는 유저,그룹 가져오기")
    @GetMapping("/{aclId}")
    public Page<AceDto> getAceListByAclId(@PathVariable String aclId, Pageable pageable) {
        return aceService.getAceListByAclId(aclId,pageable);
    }

}
