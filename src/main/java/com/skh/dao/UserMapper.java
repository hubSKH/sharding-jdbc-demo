package com.skh.dao;

import com.skh.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User record);

    User selectByPrimaryKey(int id);
}
