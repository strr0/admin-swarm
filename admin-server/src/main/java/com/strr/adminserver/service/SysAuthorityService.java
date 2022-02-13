package com.strr.adminserver.service;

import com.strr.adminserver.model.SysAuthority;
import com.strr.adminserver.model.SysAuthorityVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysAuthorityService {
    SysAuthority get(Integer id);

    List<SysAuthority> list(SysAuthorityVO param);

    int save(SysAuthority record);

    int remove(Integer id);

    int update(SysAuthority record);

    Page<SysAuthority> page(SysAuthorityVO param, Pageable pageable);

    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    List<SysAuthority> listByUserId(Integer userId);

    /**
     * 删除权限
     * @param id
     */
    void removeWithRel(Integer id);
}