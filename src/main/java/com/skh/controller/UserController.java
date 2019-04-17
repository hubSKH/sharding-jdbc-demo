package com.skh.controller;

import com.skh.po.User;
import com.skh.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("select")
    public User getuser(@RequestParam("id")int id){

        return userService.getUser (id);

    }
}
