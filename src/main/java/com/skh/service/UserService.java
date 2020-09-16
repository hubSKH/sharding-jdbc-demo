package com.skh.service;

import com.skh.po.User;

import java.util.List;

public interface UserService {

    User getUser(Long id);

    List<User> getUserList(Long id);

    Long saveUser(User user);
}
