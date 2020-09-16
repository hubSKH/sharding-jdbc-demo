package com.skh.controller;

import com.skh.po.User;
import com.skh.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("select")
    public User getuser(@RequestParam("id")Long id){

        return userService.getUser (id);

    }

    @PostMapping("save")
    public Long saveUser(@RequestBody User user){

        return userService.saveUser (user);

    }
}
