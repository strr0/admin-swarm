package com.strr.adminserver.service.impl;

import com.strr.adminserver.mapper.SysRoleMapper;
import com.strr.adminserver.model.SysRole;
import com.strr.adminserver.model.SysRoleVO;
import com.strr.adminserver.service.SysRoleService;
import com.strr.adminserver.util.SysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    private final SysRoleMapper sysRoleMapper;

    @Autowired
    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    public SysRole get(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    public List<SysRole> list(SysRoleVO param) {
        return sysRoleMapper.selectByParam(param);
    }

    public int save(SysRole record) {
        return sysRoleMapper.insert(record);
    }

    public int remove(Integer id) {
        return sysRoleMapper.deleteByPrimaryKey(id);
    }

    public int update(SysRole record) {
        return sysRoleMapper.updateByPrimaryKey(record);
    }

    public Page<SysRole> page(SysRoleVO param, Pageable pageable) {
        long count = sysRoleMapper.countByParam(param);
        List<SysRole> list = sysRoleMapper.selectByParamSelective(param, pageable);
        return new PageImpl<>(list, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), count);
    }

    /**
     * 更新角色权限
     * @param rid
     * @param oldAids
     * @param newAids
     */
    @Override
    public void updateRel(Integer rid, Integer[] oldAids, Integer[] newAids) {
        for (Integer aid : SysUtil.subtraction(oldAids, newAids)) {
            sysRoleMapper.removeRel(rid, aid);
        }
        for (Integer aid : SysUtil.subtraction(newAids, oldAids)) {
            sysRoleMapper.saveRel(rid, aid);
        }
    }

    /**
     * 获取角色权限
     * @param rid
     * @return
     */
    @Override
    public List<Integer> listRelByRid(Integer rid) {
        return sysRoleMapper.listRelByRid(rid);
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    public void removeWithRel(Integer id) {
        sysRoleMapper.deleteByPrimaryKey(id);
        sysRoleMapper.deleteRARelByRid(id);
        sysRoleMapper.deleteURRelByRid(id);
    }
}