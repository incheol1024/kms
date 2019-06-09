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
import com.devworker.kms.exception.NotAllowException;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.site.ProjectBoardRepo;
import com.devworker.kms.repo.site.ProjectRepo;
import com.devworker.kms.repo.site.SiteRepo;
import com.devworker.kms.util.AclUtil;
import com.devworker.kms.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
          return new PageImpl(collect);
        //return boardWriteService.getPages(collect, pageable);
    }

    public int addProject(ProjectDto dto) {
        return projectRepo.save(dto.getDao()).getProjectId();
    }

    public void deleteProject(int siteId, int projectId) {
        projectRepo.deleteById(projectId);
    }

    public void addBoard(ProjectBoardDto projectBoardDto) {
        //TODO::
        projectBoardDto.getBoardDetailDto().setRegDate(LocalDateTime.now());
        projectBoardDto.getBoardDetailDto().setUpdDate(LocalDateTime.now());
        projectBoardDto.getBoardDetailDto().setUserId(CommonUtil.getCurrentUser());
        projectBoardDto.setBoardId((boardWriteService.register(projectBoardDto.getBoardDetailDto())));

       projectBoardRepo.save(projectBoardDto.getDao());
    }
    public void editBoard(ProjectBoardDto projectBoardDto) {
        //TODO::
        projectBoardDto.getBoardDetailDto().setUpdDate(LocalDateTime.now());
        projectBoardDto.getBoardDetailDto().setUserId(CommonUtil.getCurrentUser());

        boardWriteService.register(projectBoardDto.getBoardDetailDto());
    }

    public void deleteBoard(int siteId, int projectId, long boardId) {
        //TODO::
        BoardDetailDto boardDetailDto=boardWriteService.getBoard(boardId);
        if(!AclUtil.checkPermission(boardDetailDto.getUserId() , PermissionType.DELETESOL))
            throw new NotAllowException("You Have not Delete Project Board Permission");
       	projectBoardRepo.deleteById(boardId);
        boardWriteService.delete(boardId);
    }
    public BoardDetailDto getSiteProjectBoardById(Long id) {
        Optional<ProjectBoardDao> projectBoardDao =  projectBoardRepo.findByBoardId(id);
        projectBoardDao.orElseThrow(() -> new NotExistException("Site Board Not Found"));
        BoardDetailDto dto = boardWriteService.getBoard(id);
        dto.setReadOnly(!AclUtil.checkPermission(dto.getUserId() , PermissionType.MODIFYSOL));
        dto.setHits(dto.getHits() +1);
        boardWriteService.edit(dto);
        return dto;
    }
}
