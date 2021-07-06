package com.study.guliedu.user.controller;


import com.study.guliedu.service_base.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author autoGenerator
 * @since 2021-06-30
 */
@RestController
@RequestMapping("/edu/user")
public class AclUserController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin-token");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("introduction","I am a super administrator")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("name","Super Admin");
    }



}

