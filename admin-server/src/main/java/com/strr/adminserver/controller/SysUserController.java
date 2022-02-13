package com.strr.adminserver.controller;

import com.strr.adminserver.model.SysUser;
import com.strr.adminserver.model.SysUserVO;
import com.strr.adminserver.service.SysUserService;
import com.strr.adminserver.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/sysUser")
public class SysUserController {
    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("/get")
    public Result<SysUser> get(Integer id) {
        SysUser sysUser = sysUserService.get(id);
        if (sysUser != null) {
            return Result.ok();
        }
        return Result.error();
    }

    @GetMapping("/list")
    public Result<List<SysUser>> list(SysUserVO param) {
        List<SysUser> sysUserList = sysUserService.list(param);
        return Result.ok(sysUserList);
    }

    @PostMapping("/save")
    public Result<SysUser> save(SysUser record) {
        if (sysUserService.save(record) == 1) {
            return Result.ok(record);
        }
        return Result.error();
    }

    @DeleteMapping("/remove")
    public Result<Integer> remove(Integer id) {
        if (sysUserService.remove(id) == 1) {
            return Result.ok();
        }
        return Result.error();
    }

    @PutMapping("/update")
    public Result<SysUser> update(SysUser record) {
        if (sysUserService.update(record) == 1) {
            return Result.ok(record);
        }
        return Result.error();
    }

    @GetMapping("/page")
    public Page<SysUser> page(SysUserVO param, Pageable pageable) {
        return sysUserService.page(param, pageable);
    }

    /**
     * 保存用户信息
     * @param sysUser
     * @param oldRids
     * @param newRids
     * @return
     */
    @PostMapping("/saveInfo")
    public Result<Void> saveInfo(SysUser sysUser, Integer[] oldRids, Integer[] newRids) {
        sysUserService.saveWithRel(sysUser, oldRids, newRids);
        return Result.ok();
    }

    /**
     * 获取用户角色
     * @param uid
     * @return
     */
    @GetMapping("/listRelByUid")
    public Result<List<Integer>> listRelByUid(Integer uid) {
        List<Integer> data = sysUserService.listRelByUid(uid);
        return Result.ok(data);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/removeInfo")
    public Result<Void> removeInfo(Integer id) {
        sysUserService.removeWithRel(id);
        return Result.ok();
    }

    /**
     * 认证
     * @param authentication
     * @return
     */
    @GetMapping(value = "/auth", produces = "application/json")
    public Map<String, Object> auth(OAuth2Authentication authentication) {
        Map<String, Object> auth = new HashMap<>();
        auth.put("user", authentication.getUserAuthentication().getPrincipal());
        auth.put("authorities", authentication.getUserAuthentication().getAuthorities());
        return auth;
    }
}