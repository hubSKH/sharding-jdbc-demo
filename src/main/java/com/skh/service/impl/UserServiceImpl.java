package com.skh.service.impl;

import com.skh.dao.UserMapper;
import com.skh.po.User;
import com.skh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger (UserServiceImpl.class);
    /**
     * 注入数据接口
     */
    @Autowired
    private UserMapper vtsUserMapper;


    @Override
    public User getUser(Long id) {
        User user = vtsUserMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public List<User> getUserList(Long id) {
        return vtsUserMapper.selectList (id);
    }

    @Override
    public Long saveUser(User user) {
        vtsUserMapper.insert(user);
        return user.getId ();
    }

}

