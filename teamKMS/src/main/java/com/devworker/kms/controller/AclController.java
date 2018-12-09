package com.devworker.kms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.service.acl.AceService;
import com.devworker.kms.service.acl.AclService;

@RestController
public class AclController {
	@Autowired
	AceService aceService;
	@Autowired
	AclService aclService;
}
