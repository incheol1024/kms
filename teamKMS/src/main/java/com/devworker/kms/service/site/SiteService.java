package com.devworker.kms.service.site;

import com.devworker.kms.component.BoardComponent;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.common.BoardDetailDto;
import com.devworker.kms.dto.common.BoardDto;
import com.devworker.kms.dto.site.ProjectBoardDto;
import com.devworker.kms.dto.site.ProjectDto;
import com.devworker.kms.dto.site.SiteDto;
import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.site.ProjectBoardDao;
import com.devworker.kms.entity.site.ProjectDao;
import com.devworker.kms.entity.site.SiteDao;
import com.devworker.kms.exception.NotAllowException;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.site.ProjectBoardRepo;
import com.devworker.kms.repo.site.ProjectBoardRepoImpl;
import com.devworker.kms.repo.site.ProjectRepo;
import com.devworker.kms.repo.site.SiteRepo;
import com.devworker.kms.util.AclUtil;
import com.devworker.kms.util.CommonUtil;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    @Autowired
    ProjectBoardRepoImpl projectBoardRepoImpl;

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
        return projectBoardRepoImpl.getPageList(projectId,pageable).map(BoardDao::getBoardDto);
    }

    public int addProject(ProjectDto dto) {
        return projectRepo.save(dto.getDao()).getProjectId();
    }

    public void deleteProject(int siteId, int projectId) {
        projectRepo.deleteById(projectId);
    }

    public void addBoard(ProjectBoardDto projectBoardDto) {
        //TODO::
        projectBoardDto.setBoardId((boardWriteService.register(projectBoardDto.getBoardDetailDto(),PermissionType.CREATESITE)));
       projectBoardRepo.save(projectBoardDto.getDao());
    }
    public void editBoard(ProjectBoardDto projectBoardDto) {
        //TODO::
        BoardDetailDto boardDetailDto=boardWriteService.getBoard(projectBoardDto.getBoardDetailDto().getBoardId(),PermissionType.MODIFYSITE);
        boardWriteService.edit(projectBoardDto.getBoardDetailDto(),boardDetailDto,PermissionType.MODIFYSITE);
    }

    public void deleteBoard(int siteId, int projectId, long boardId) {
        //TODO::
        BoardDetailDto boardDetailDto=getSiteProjectBoardById(boardId);
        if(!AclUtil.checkPermission(boardDetailDto.getUserId() , PermissionType.DELETESITE))
            throw new NotAllowException("You Have not Delete Project Board Permission");
       	projectBoardRepo.deleteById(boardId);
        boardWriteService.delete(boardId);
    }
    public BoardDetailDto getSiteProjectBoardById(Long id) {
        projectBoardRepo.findByBoardId(id).orElseThrow(() -> new NotExistException("Site Board Not Found"));;
        return boardWriteService.getBoard(id,PermissionType.MODIFYSITE);
    }
}
