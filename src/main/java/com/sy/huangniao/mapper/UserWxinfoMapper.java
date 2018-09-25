package com.sy.huangniao.mapper;

import com.sy.huangniao.pojo.UserWxinfo;

import java.util.List;

public interface UserWxinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserWxinfo record);

    int insertSelective(UserWxinfo record);

    UserWxinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserWxinfo record);

    int updateByPrimaryKey(UserWxinfo record);

    List<UserWxinfo> selectList(UserWxinfo userWxinfo);

    int updateByUserIdAndOpenid(UserWxinfo userWxinfo);
}