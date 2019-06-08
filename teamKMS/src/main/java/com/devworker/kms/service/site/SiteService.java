package com.devworker.kms.service.site;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.site.ProjectBoardDto;
import com.devworker.kms.dto.site.ProjectDto;
import com.devworker.kms.dto.site.SiteDto;
import com.devworker.kms.entity.site.ProjectBoardDao;
import com.devworker.kms.entity.site.ProjectDao;
import com.devworker.kms.entity.site.SiteDao;
import com.devworker.kms.repo.site.ProjectBoardRepo;
import com.devworker.kms.repo.site.ProjectRepo;
import com.devworker.kms.repo.site.SiteRepo;
import com.devworker.kms.util.AclUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {
    @Autowired
    SiteRepo siteRepo;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    ProjectBoardRepo projectBoardRepo;
    @Autowired
    BoardComponent boardWriteService;

    public List<SiteDto> getAllSite(int menuId) {
        return siteRepo.getAllSite(menuId).stream().map(SiteDao::getDto).collect(Collectors.toList());
    }

    public int addSite(SiteDto dto) {
        return siteRepo.save(dto.getDao()).getSiteId();
    }

    public void deleteSite(int siteId) {
        siteRepo.deleteById(siteId);
    }

    public Page<ProjectDto> getSiteProjects(int menuId, int siteId, Pageable pageable) {
        return projectRepo.getSiteProjects(siteId, pageable).map(ProjectDao::getDto);
    }

    public Page<BoardDto> getProjectBoards(int menuId, int siteId, int projectId, Pageable pageable) {
         Page<ProjectBoardDto> projectBoardDtoLIst=projectBoardRepo.getSiteProjects(projectId, pageable).map(ProjectBoardDao::getDto);
         List<Long> collect =new ArrayList<>();

        for(ProjectBoardDto  projectDto : projectBoardDtoLIst)
            collect.add(projectDto.getBoardId());
        return boardWriteService.getPages(collect, pageable);
    }

    public int addProject(ProjectDto dto) {
        return projectRepo.save(dto.getDao()).getProjectId();
    }

    public void deleteProject(int siteId, int projectId) {
        projectRepo.deleteById(projectId);
    }

    public void addBoard(BoardDetailDto dto, int siteId, int projectId) {
        //TODO::
    	ProjectBoardDao projectBoardDao=new ProjectBoardDao();
    	projectBoardDao.setBoardId((boardWriteService.register(dto)));
    	projectBoardDao.setProjectId(projectId);
    	
       projectBoardRepo.save(projectBoardDao);
    }

    public void deleteBoard(int siteId, int projectId, int boardId) {
        //TODO::
    	projectBoardRepo.deleteById(boardId);
    }
    public BoardDetailDto getSiteProjectBoardById(Long id) {
       /* Optional<SiteDao> siteDao =  projectRepo.findById(id);
        siteDao.orElseThrow(() -> new NotExistException("Site Board Not Found"));*/
        BoardDetailDto dto = boardWriteService.getBoard(id);
        dto.setReadOnly(!AclUtil.checkPermission(dto.getUserId() , PermissionType.MODIFYSOL));
        dto.setHits(dto.getHits() +1);
        boardWriteService.edit(dto);
        return dto;
    }
}
