package com.devworker.kms.controller.site;

import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.site.ProjectBoardDto;
import com.devworker.kms.dto.site.ProjectDto;
import com.devworker.kms.dto.site.SiteDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.service.site.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/site")
public class SiteController {
    @Autowired
    SiteService siteService;

    @GetMapping("/{menuId}")
    public List<SiteDto> getSites(@PathVariable int menuId){
        return siteService.getAllSite(menuId);
    }

    @PutMapping
    public int addSite(@RequestBody SiteDto dto){
        return siteService.addSite(dto);
    }

    @DeleteMapping("/{siteId}")
    public void deleteSite(@PathVariable int siteId){
        siteService.deleteSite(siteId);
    }

    @GetMapping("/{menuId}/{siteId}")
    public Page<ProjectDto> getSiteProjects(@PathVariable int menuId, @PathVariable int siteId, Pageable pageable){
        return siteService.getSiteProjects(menuId, siteId,pageable);
    }

    @PutMapping("/{siteId}")
    public int addProject(@RequestBody ProjectDto dto, @PathVariable String siteId){
        return siteService.addProject(dto);
    }

    @DeleteMapping("/{siteId}/{projectId}")
    public void deleteProject(@PathVariable int siteId, @PathVariable int projectId){
        siteService.deleteProject(siteId, projectId);
    }

    @GetMapping("/{menuId}/{siteId}/{projectId}")
    public Page<ProjectBoardDto> getProjectBoards(@PathVariable int menuId, @PathVariable int siteId, @PathVariable int projectId, Pageable pageable){
        return siteService.getProjectBoards(menuId,siteId,projectId,pageable);
    }

    @PutMapping("/{siteId}/{projectId}")
    public void addProjectBoards(@RequestBody BoardDto boardDto, @PathVariable int siteId, @PathVariable int projectId){
         siteService.addBoard(boardDto, siteId, projectId);
    }

    @DeleteMapping("/{siteId}/{projectId}/{boardId}")
    public void deleteBoard(@PathVariable int siteId, @PathVariable int projectId, @PathVariable int boardId){
        siteService.deleteBoard(siteId, projectId, boardId);
    }
}
