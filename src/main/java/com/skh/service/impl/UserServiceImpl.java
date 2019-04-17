package com.skh.service.impl;

import com.skh.dao.UserMapper;
import com.skh.po.User;
import com.skh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    /**
     * 注入数据接口
     */
    @Autowired
    private UserMapper vtsUserMapper;


    @Override
    public User getUser(int id) {
        User user = vtsUserMapper.selectByPrimaryKey(id);
        System.out.println(user);
        return user;
    }

    @Override
    public Integer saveUser(User user) {
        vtsUserMapper.insert(user);
        return user.getId ();
    }

}

