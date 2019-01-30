package com.devworker.kms.controller.acl;

import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.service.acl.AceService;
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

    @PutMapping
    public String addAce(@Valid @RequestBody AceDto dto) {
        return aceService.addAce(dto);
    }

    @PostMapping
    public void updateAce(@Valid  @RequestBody AceDto dto) {
        aceService.updateAce(dto);
    }

    @DeleteMapping("/{aclId}/{aceId}")
    public void deleteAce(@PathVariable String aclId, @PathVariable String aceId) {
        AceDto dto = new AceDto();
        dto.setAclId(aclId);
        dto.setAceId(aceId);
        aceService.deleteAce(dto);
    }

    @GetMapping
    public Page<AceDto> getAceList(Pageable pageable) {
        return aceService.getAceList(pageable);
    }

    @GetMapping("/{aclId}")
    public Page<AceDto> getAceListByAclId(@PathVariable String aclId, Pageable pageable) {
        return aceService.getAceListByAclId(aclId,pageable);
    }

}
