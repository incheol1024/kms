package com.devworker.kms.service;

import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.util.AclUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private final
    UserRepo repo;
    private final
    GroupService service;
    private final
    PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepo repo, @Lazy GroupService service, PasswordEncoder encoder) {
        this.repo = repo;
        this.service = service;
        this.encoder = encoder;
    }

    @Override
    @Cacheable(key = "'userCount'", value = "userCache")
    public long getCount() {
        return repo.count();
    }

    @Override
    public Page<UserDto> getUserListPage(Pageable pageable) {
        Page<UserDao> daoPage = repo.findAll(pageable);
        return daoPage.map(UserDao::getDto).map(userDto -> {
            userDto.setGroupName(service.getGroup(userDto.getGroupId()).getName());
            return userDto;
        });
    }


    @CacheEvict(key = "'userCount'", value = "userCache")
    public UserDto addUser(UserDto dto) {
        AclUtil.checkPermission(PermissionType.CREATEUSER);
        if (repo.existsById(dto.getId()))
            throw new DuplicateKeyException("User Id already Has in Server");
        dto.setPassword(encoder.encode(dto.getPassword()));
        String groupName = service.getGroup(dto.getGroupId()).getName();
        dto.setGroupName(groupName);
        repo.save(dto.getDao());
        return dto;
    }

    @CacheEvict(key = "'userCount'", value = "userCache")
    public void deleteUser(String id) {
        AclUtil.checkPermission(PermissionType.DELETEUSER);
        repo.deleteById(id);
    }

    public void updateUser(UserDto dto) {
        AclUtil.checkPermission(PermissionType.MODIFYUSER);
        UserDto dbUser = getUser(dto.getId());
        if (!dto.getPassword().equals(dbUser.getPassword()) && !encoder.matches(dto.getPassword(), dbUser.getPassword()))
            dto.setPassword(encoder.encode(dto.getPassword()));
        repo.save(dto.getDao());
    }

    @Override
    public UserDto getUser(String id) {
        return repo.findById(id).map(UserDao::getDto).map(userDto -> {
            userDto.setGroupName(service.getGroup(userDto.getGroupId()).getName());
            return userDto;
        }).orElseThrow(() -> new NotExistException("User not exist"));
    }

    @Override
    public Page<UserDto> getGroupedUser(int id, Pageable pageable) {
        Page<UserDao> daoList = repo.getGroupedUser(id, pageable);
        return getDaoToDtoList(daoList, false);
    }

    @Override
    public Page<UserDto> getUserList(Pageable pageable) {
        Page<UserDao> daoList = repo.findAll(pageable);
        return getDaoToDtoList(daoList, true);
    }

    private Page<UserDto> getDaoToDtoList(Page<UserDao> daoList, boolean addGroupName) {
        return daoList.map(UserDao::getDto)
                .map(userDto -> {
                    if (addGroupName)
                        service.getGroup(userDto.getGroupId()).setName(userDto.getGroupName());
                    return userDto;
                });
    }


}
