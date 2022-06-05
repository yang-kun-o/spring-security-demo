package com.yk.root.controller;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    //SecurityExpressionRoot
    @PreAuthorize("hasAuthority('test')")//配置权限
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello2")
    @PreAuthorize("hasAuthority('test2')")//配置权限
    public String hello2(){
        return "hello2";
    }

    @GetMapping("/hello3")
    @PreAuthorize("hasAnyAuthority('admin','test','test2')")//配置权限
    public String hello3(){
        return "hello3";
    }

    @GetMapping("/hello4")
    @PreAuthorize("hasRole('管理员')")//配置权限
    public String hello4(){
        return "hello4";
    }
}
