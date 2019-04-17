package com.skh.service;

import com.skh.po.User;

public interface UserService {

    User getUser(int id);

    Integer saveUser(User user);
}
