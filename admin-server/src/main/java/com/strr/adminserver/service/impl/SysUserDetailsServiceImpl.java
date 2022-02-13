package com.strr.adminserver.service.impl;

import com.strr.adminserver.mapper.SysAuthorityMapper;
import com.strr.adminserver.mapper.SysRoleMapper;
import com.strr.adminserver.mapper.SysUserMapper;
import com.strr.adminserver.model.SysUserDetails;
import com.strr.adminserver.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserDetailsServiceImpl implements SysUserDetailsService {
    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysAuthorityMapper sysAuthorityMapper;

    @Autowired
    public SysUserDetailsServiceImpl(SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper,
                                     SysAuthorityMapper sysAuthorityMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysAuthorityMapper = sysAuthorityMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SysUserDetails> userList = sysUserMapper.getByUsername(username);
        if (!userList.isEmpty()) {
            SysUserDetails user = userList.get(0);
            user.setRoleList(sysRoleMapper.listByUserId(user.getId()));
            user.setAuthorityList(sysAuthorityMapper.listByUserId(user.getId()));
            return user;
        }
        throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
    }
}
