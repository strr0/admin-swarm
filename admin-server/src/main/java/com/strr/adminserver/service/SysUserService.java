package com.strr.adminserver.service;

import com.strr.adminserver.model.SysUser;
import com.strr.adminserver.model.SysUserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysUserService {
    SysUser get(Integer id);

    List<SysUser> list(SysUserVO param);

    int save(SysUser record);

    int remove(Integer id);

    int update(SysUser record);

    Page<SysUser> page(SysUserVO param, Pageable pageable);

    /**
     * 保存用户
     * @param sysUser
     * @param oldRids
     * @param newRids
     */
    void saveWithRel(SysUser sysUser, Integer[] oldRids, Integer[] newRids);

    /**
     * 获取用户角色
     * @param uid
     * @return
     */
    List<Integer> listRelByUid(Integer uid);

    /**
     * 删除用户
     * @param id
     */
    void removeWithRel(Integer id);
}