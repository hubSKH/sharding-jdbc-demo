package com.skh.service;

import com.skh.po.User;

public interface UserService {

    User getUser(int id);

    Long saveUser(User user);
}
