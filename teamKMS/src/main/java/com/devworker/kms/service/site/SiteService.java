package com.devworker.kms.service.site;

import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.site.ProjectBoardDto;
import com.devworker.kms.dto.site.ProjectDto;
import com.devworker.kms.dto.site.SiteDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.site.ProjectBoardDao;
import com.devworker.kms.entity.site.ProjectDao;
import com.devworker.kms.entity.site.SiteDao;
import com.devworker.kms.repo.site.ProjectBoardRepo;
import com.devworker.kms.repo.site.ProjectRepo;
import com.devworker.kms.repo.site.SiteRepo;
import com.devworker.kms.component.BoardComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public Page<BoardDetailDto> getProjectBoards(int menuId, int siteId, int projectId, Pageable pageable) {
         Page<ProjectBoardDto> projectBoardDtoLIst=projectBoardRepo.getSiteProjects(projectId, pageable).map(ProjectBoardDao::getDto);
         List<BoardDetailDto> BoardDtoLIst= new ArrayList<>();

        for(ProjectBoardDto  projectDto : projectBoardDtoLIst)
            BoardDtoLIst.add(boardWriteService.getBoard(projectDto.getBoardId()));

        return new PageImpl(BoardDtoLIst, pageable, BoardDtoLIst.size());
        //return projectBoardRepo.getSiteProjects(projectId, pageable).map(ProjectBoardDao::getDto);
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
}
