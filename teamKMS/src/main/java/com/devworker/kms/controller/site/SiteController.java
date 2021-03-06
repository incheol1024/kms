package com.devworker.kms.controller.site;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.site.ProjectBoardDto;
import com.devworker.kms.dto.site.ProjectDto;
import com.devworker.kms.dto.site.SiteDto;
import com.devworker.kms.service.site.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    public int addSite(@RequestBody SiteDto dto){
        return siteService.addSite(dto);
    }

    @DeleteMapping("/{siteId}")
    public void deleteSite(@PathVariable int siteId){
        siteService.deleteSite(siteId);
    }

    @GetMapping("/{menuId}/{siteId}")
    public Page<ProjectDto> getSiteProjects(@PathVariable int menuId, @PathVariable int siteId,  Pageable pageable){
        return siteService.getSiteProjects(menuId, siteId,pageable);
    }

    @PostMapping("/{siteId}")
    public int addProject(@RequestBody ProjectDto dto, @PathVariable String siteId){
        return siteService.addProject(dto);
    }
    @PutMapping("/editProject")
    public int editProject(@RequestBody ProjectDto dto, @PathVariable String siteId){
        return siteService.editProject(dto);
    }
    @DeleteMapping("/{siteId}/{projectId}")
    public void deleteProject(@PathVariable int siteId, @PathVariable int projectId){
        siteService.deleteProject(siteId, projectId);
    }

    @GetMapping("/{menuId}/{siteId}/{projectId}")
    public Page<BoardDto> getProjectBoardLists(@PathVariable int menuId, @PathVariable int siteId, @PathVariable int projectId, Pageable pageable){

        return siteService.getProjectBoards(menuId,siteId,projectId,pageable);
    }
     @GetMapping("/{menuId}/{siteId}/{projectId}/{boardId}")
    public BoardDetailDto getProjectBoardByDetail(@PathVariable int menuId,@PathVariable int siteId, @PathVariable int projectId, @PathVariable Long boardId){
         return siteService.getSiteProjectBoardById(boardId);
    }

    @PostMapping("/add")
    public void addProjectBoards(@Valid @RequestBody ProjectBoardDto projectBoardDto){
        siteService.addBoard(projectBoardDto);
    }
    @PutMapping("/edit")
    public void editProjectBoards(@Valid @RequestBody ProjectBoardDto projectBoardDto){
        siteService.editBoard(projectBoardDto);
    }

    @DeleteMapping("/{siteId}/{projectId}/{boardId}")
    public void deleteBoard(@PathVariable int siteId, @PathVariable int projectId, @PathVariable int boardId){
        siteService.deleteBoard(siteId, projectId, boardId);
    }
}
