package com.skh.dao;

import com.skh.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectList(Long id);
}
