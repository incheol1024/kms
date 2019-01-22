package com.devworker.kms.controller.acl;

import com.devworker.kms.dto.acl.AclDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AceController {

    @PutMapping("/acl")
    public String addAcl(@RequestBody AclDto dto){
        return "";
    }
}
