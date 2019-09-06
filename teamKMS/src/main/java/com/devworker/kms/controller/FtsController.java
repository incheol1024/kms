package com.devworker.kms.controller;

import com.devworker.kms.fts.FTSDao;
import com.devworker.kms.service.FTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fts")
public class FtsController {
    @Autowired
    FTSService ftsService;

    @GetMapping
    public Iterable<FTSDao> getSearchData(@RequestParam String word) {
        return ftsService.find(word);
    }
}
