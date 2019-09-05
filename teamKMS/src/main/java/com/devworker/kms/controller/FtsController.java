package com.devworker.kms.controller;

import com.devworker.kms.dto.FtsDto;
import com.devworker.kms.service.FTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fts")
public class FtsController {
    @Autowired
    FTSService ftsService;

    @GetMapping
    public List<FtsDto> getSearchData(@RequestParam String word) {
        return ftsService.findByUser(word);
    }
}
