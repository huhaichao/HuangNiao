package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.UserInfo;

import java.util.List;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    public List<UserInfo> selectList(UserInfo record);
}