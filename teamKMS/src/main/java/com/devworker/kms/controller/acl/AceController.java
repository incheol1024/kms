package com.devworker.kms.controller.acl;

import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.service.acl.AceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class AceController {
    @Autowired
    AceService aceService;

    @PutMapping("/ace")
    public String addAce(@RequestBody AceDto dto) {
        return aceService.addAce(dto);
    }

    @PostMapping("/ace")
    public void updateAce(@RequestBody AceDto dto) {
        aceService.updateAce(dto);
    }

    @DeleteMapping("/ace/{aclId}/{aceId}")
    public void deleteAce(@PathVariable String aclId, @PathVariable String aceId){
        aceService.deleteAce(aclId,aceId);
    }

    @GetMapping("/ace")
    public Page<AceDto> getAceList(Pageable pageable){
        return aceService.getAceList(pageable);
    }

    @GetMapping("/ace/{aclId}")
    public Page<AceDto> getAceListByAclId(@PathVariable String aclId, Pageable pageable) {
        return aceService.getAceListByAclId(aclId,pageable);
    }

}
